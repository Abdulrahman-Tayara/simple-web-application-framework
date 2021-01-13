package ast.visitor;


import ast.nodes.attribute.*;
import ast.nodes.expression.condition.*;
import ast.nodes.expression.math.MathematicalExpressionNode;
import ast.nodes.expression.value.ConcatableNode;
import ast.nodes.expression.value.IndexableNode;
import ast.nodes.expression.value.IterableNode;
import ast.nodes.expression.value.ValuableNode;
import ast.nodes.expression.value.literal.*;
import ast.nodes.expression.value.variable.ConcatVariableExpressionNode;
import ast.nodes.expression.value.variable.FunctionExpressionNode;
import ast.nodes.expression.value.variable.IndexedVariableExpressionNode;
import ast.nodes.html.*;
import ast.nodes.expression.value.variable.VariableExpressionNode;
import gen.HTMLParser;
import gen.HTMLParserBaseVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BaseVisitor extends HTMLParserBaseVisitor {
    @Override
    public HtmlDocumentNode visitHtmlDocument(HTMLParser.HtmlDocumentContext ctx) {
        List<HTMLElementNode> elements = new ArrayList<HTMLElementNode>();
        HtmlDocumentNode doc = new HtmlDocumentNode();
        for (int i = 0; i < ctx.htmlElements().size(); i++) {
            elements.add(visitHtmlElements(ctx.htmlElements(i)));
        }
        doc.setElements(elements);
        return doc;
    }

    @Override
    public HTMLElementNode visitHtmlElements(HTMLParser.HtmlElementsContext ctx) {
        return visitHtmlElement(ctx.htmlElement());
    }

    @Override
    public Object visitVariableScopeContent(HTMLParser.VariableScopeContentContext ctx) {
        // TODO implement it
        return super.visitVariableScopeContent(ctx);
    }

    @Override
    public HTMLTextNode visitHtmlChardata(HTMLParser.HtmlChardataContext ctx) {
        HTMLTextNode node = new HTMLTextNode();

        StringBuilder htmlText = new StringBuilder();

        if (ctx.SEA_WS() != null)
            htmlText.append(ctx.SEA_WS().getText());

        if (ctx.HTML_TEXT() != null)
            htmlText.append(ctx.HTML_TEXT().getText());

        ctx.htmlChardata().forEach(htmlChardataContext -> htmlText.append(visitHtmlChardata(htmlChardataContext)));

        node.setText(htmlText.toString());

        return node;
    }

    @Override
    public List<HTMLElementNode> visitHtmlContent(HTMLParser.HtmlContentContext ctx) {
        List<HTMLElementNode> elements =
                // Iterate over children
                ctx.children.stream().filter( // Filter the contexts to HTMLElement or HTMLCharData
                        child -> child instanceof HTMLParser.HtmlElementContext
                                || child instanceof HTMLParser.HtmlChardataContext
                ).map(context -> (HTMLElementNode) visit(context)) // Visit the filtered contexts
                        .collect(Collectors.toList());

        return elements;
    }

    @Override
    public HTMLElementNode visitHtmlElement(HTMLParser.HtmlElementContext ctx) {
        if (ctx.TAG_OPEN(0) != null) { // Tag

            // Check if the opened tag name not equals the closed tag name
            if (ctx.TAG_NAME(1) != null && !ctx.TAG_NAME(0).getText().equals(ctx.TAG_NAME(1).getText())) {
                throw new RuntimeException("The closed tag name isn't the same opened tag name");
            }

            HTMLTagNode tag = new HTMLTagNode();

            // Set Tag Name
            tag.setName(ctx.TAG_NAME(0).getText());

            // Add tag attributes ex: <Tag attribute1, attribute2, ... > ... </Tag>
            ctx.htmlAttribute().forEach(attribute -> {
                tag.getAttributes().add(visitHtmlAttribute(attribute));
            });

            if (ctx.htmlContent() != null)
                tag.setContent(visitHtmlContent(ctx.htmlContent()));

            return tag;
        }
        return new HTMLElementNode();
    }

    @Override
    public AttributeNode<?> visitHtmlAttribute(HTMLParser.HtmlAttributeContext ctx) {
        /*
         * Tag attribute style: <Tag attribute_name=attribute_value>...</Tag>
         */

        if (ctx.TAG_NAME() != null) { // Standard tag (No CP attribute)
            HTMLAttributeNode attribute = new HTMLAttributeNode();
            attribute.setName(ctx.TAG_NAME().getText()); // Add name
            attribute.setValue(ctx.ATTVALUE_VALUE().getText()); // Add value

            return attribute;
        }

        // CP attribute
        return (AttributeNode<?>) super.visitHtmlAttribute(ctx);
    }

    @Override
    public Object visitParenthesizedExpression(HTMLParser.ParenthesizedExpressionContext ctx) {
        return visit(ctx.expression());
    }

    //----------------------- Literal Values -----------------------------

    @Override
    public BooleanValueNode visitLiteralBooleanExpression(HTMLParser.LiteralBooleanExpressionContext ctx) {
        BooleanValueNode node = new BooleanValueNode();
        node.setValue(Boolean.valueOf(ctx.literalBooleanValue().BOOLEAN_VALUE().getText().toLowerCase()));
        return node;
    }

    @Override
    public StringValueNode visitLiteralStringExpression(HTMLParser.LiteralStringExpressionContext ctx) {
        StringValueNode node = new StringValueNode();
        node.setValue(ctx.literalStringValue().STRING_VALUE().getText().replace("'", ""));
        return node;
    }

    @Override
    public NumericValueNode visitLiteralNumericExpression(HTMLParser.LiteralNumericExpressionContext ctx) {
        NumericValueNode node = new NumericValueNode();
        node.setValue(Float.valueOf(ctx.literalNumericValue().NUMERIC_VALUE().getText()));
        return node;
    }

    @Override
    public ArrayNode.ArrayItemNode visitArrayItemValue(HTMLParser.ArrayItemValueContext ctx) {
        ArrayNode.ArrayItemNode node = new ArrayNode.ArrayItemNode();

        // Visit the expression
        Object result = visit(ctx.expression());

        // Check if the result is valuable node
        if (result instanceof ValuableNode) {
            // Set the value
            node.setValue((ValuableNode) result);

            return node;
        }

        throw new RuntimeException("Invalid array item");
    }

    @Override
    public ArrayNode visitLiteralArray(HTMLParser.LiteralArrayContext ctx) {
        ArrayNode node = new ArrayNode();

        // Visit all items and put them.
        node.setValue(ctx.arrayItemValue().stream().map(this::visitArrayItemValue).collect(Collectors.toList()));

        return node;
    }

    //----------------------- Variable -----------------------------

    @Override
    public VariableExpressionNode visitVariableNameExpression(HTMLParser.VariableNameExpressionContext ctx) {
        VariableExpressionNode node = new VariableExpressionNode();
        node.setVariableName(ctx.variableName().ANY_NAME().getText());
        return node;
    }


    @Override
    public List<FunctionExpressionNode.FunctionParam> visitFunctionParams(HTMLParser.FunctionParamsContext ctx) {
        List<FunctionExpressionNode.FunctionParam> params = new ArrayList<>();

        // Visit all expressions
        ctx.expression().forEach(expressionContext -> {
            Object result = visit(expressionContext);

            // Check if the result is valid param
            if (result instanceof ValuableNode) {
                params.add(new FunctionExpressionNode.FunctionParam((ValuableNode) result));
            } else
                throw new RuntimeException("Invalid function param");
        });

        return params;
    }

    @Override
    public Object visitFunctionCallExpression(HTMLParser.FunctionCallExpressionContext ctx) {
        FunctionExpressionNode node = new FunctionExpressionNode();

        // Check if the function name expression is valid
        if (ctx.expression() instanceof HTMLParser.VariableNameExpressionContext) {
            // Fetch function name and set it.
            VariableExpressionNode variableExpressionNode = visitVariableNameExpression((HTMLParser.VariableNameExpressionContext) ctx.expression());
            node.setFunctionName(variableExpressionNode.getVariableName());

            // Fetch function params and set it.
            node.setParams(visitFunctionParams(ctx.functionParams()));

            return node;
        }

        throw new RuntimeException("Invalid function");
    }

    @Override
    public ConcatVariableExpressionNode visitVariableConcatExpression(HTMLParser.VariableConcatExpressionContext ctx) {
        ConcatVariableExpressionNode node = new ConcatVariableExpressionNode();
        List<ConcatableNode> values = new ArrayList<>();

        // Visit all nested expressions
        ctx.expression().forEach(expressionContext -> {
            Object visitResult = visit(expressionContext);

            // Check the visited result validation
            if (visitResult instanceof ConcatableNode) {
                // Check if the result is also a ConcatVariableExpressionNode to add its values
                if (visitResult instanceof ConcatVariableExpressionNode) {
                    values.addAll(((ConcatVariableExpressionNode) visitResult).getValues());
                } else { // Terminal object

                    // The object or the array mustn't be in the middle of the concatenation
                    if (!values.isEmpty() && (visitResult instanceof ObjectNode))
                        throw new RuntimeException("The expression isn't an object");

                    values.add((ConcatableNode) visitResult);
                }
            } else
                throw new RuntimeException("The expression isn't an object");
        });

        node.setValues(values);
        return node;
    }

    @Override
    public ObjectNode.ObjectItemNode visitObjectItem(HTMLParser.ObjectItemContext ctx) {
        ObjectNode.ObjectItemNode node = new ObjectNode.ObjectItemNode();

        // Set key
        node.setKey(ctx.variableName().ANY_NAME().getText());

        // Visit value expression
        Object value = visit(ctx.expression());

        // Check if the value is valid
        if (!(value instanceof ValuableNode))
            throw new RuntimeException("Invalid object value");

        node.setValue((ValuableNode) value);

        return node;
    }


    @Override
    public ObjectNode visitLiteralObjectExpression(HTMLParser.LiteralObjectExpressionContext ctx) {
        ObjectNode node = new ObjectNode();

        Map<String, ValuableNode> members = new HashMap<>();

        ctx.literalObject().objectItem() // Access items
                .stream().map(this::visitObjectItem) // Visit items
                .forEach(objectItemNode -> members.put(objectItemNode.getKey(), objectItemNode.getValue()));

        node.setValue(members);

        return node;
    }

    @Override
    public IndexedVariableExpressionNode visitIndexedVariableExpression(HTMLParser.IndexedVariableExpressionContext ctx) {
        IndexedVariableExpressionNode node = new IndexedVariableExpressionNode();

        Object indexedNode = visit(ctx.expression());

        if (indexedNode instanceof IndexableNode) {
            node.setVariable((IndexableNode) indexedNode);

            node.setIndex(visitArrayIndexExpression(ctx.arrayIndexExpression()));

            return node;
        }

        throw new RuntimeException("The expression isn't indexable");
    }

    @Override
    public IndexedVariableExpressionNode.IndexNode visitArrayIndexExpression(HTMLParser.ArrayIndexExpressionContext ctx) {
        IndexedVariableExpressionNode.IndexNode node = new IndexedVariableExpressionNode.IndexNode();

        Object expression = visit(ctx.expression());

        if (expression instanceof ValuableNode) {
            if (expression instanceof NumericValueNode) {
                Float numericValue = ((NumericValueNode) expression).getValue();
                // Check for negative index && decimal index
                if (numericValue < 0 || numericValue != Math.floor(numericValue))
                    throw new RuntimeException("Invalid index");
            }

            node.setValue((ValuableNode) expression);
            return node;
        }

        throw new RuntimeException("Invalid index");
    }

    //----------------------- Condition -----------------------------

    @Override
    public OneOperandConditionExpressionNode visitOneOperandConditionExpression(HTMLParser.OneOperandConditionExpressionContext ctx) {
        OneOperandConditionExpressionNode node = new OneOperandConditionExpressionNode();

        // Set operator
        node.setOperator(ctx.CONDITIONAL_OPERATORS_ONE_OPERAND().getText());

        Object operand = visit(ctx.expression());

        // Check if the operand is a logical node
        if (operand instanceof LogicalNode) {
            node.setOperand((LogicalNode) operand);

            return node;
        }

        throw new RuntimeException(ctx.expression().getText() + " isn't logical");
    }

    @Override
    public TwoOperandsConditionExpressionNode visitTwoOperandsConditionExpression(HTMLParser.TwoOperandsConditionExpressionContext ctx) {
        Object operand1 = visit(ctx.expression(0));
        Object operand2 = visit(ctx.expression(1));

        // Check if the operands is valid
        if (operand1 instanceof ValuableNode && operand2 instanceof ValuableNode) {
            TwoOperandsConditionExpressionNode node = new TwoOperandsConditionExpressionNode();

            node.setOperator(ctx.CONDITIONAL_OPERATORS_TWO_OPERAND().getText());
            node.setOperand1((ValuableNode) operand1);
            node.setOperand2((ValuableNode) operand2);

            return node;
        }

        throw new RuntimeException("Invalid condition expression");
    }

    @Override
    public ConcatConditionExpressionNode visitConcatConditionExpression(HTMLParser.ConcatConditionExpressionContext ctx) {
        Object operand1 = visit(ctx.expression(0));
        Object operand2 = visit(ctx.expression(1));

        // Check if the operands is valid
        if (operand1 instanceof LogicalNode && operand2 instanceof LogicalNode) {
            ConcatConditionExpressionNode node = new ConcatConditionExpressionNode();

            node.setOperator(ctx.CONDITIONAL_OPERATORS_CONCAT().getText());
            node.setCondition1((LogicalNode) operand1);
            node.setCondition2((LogicalNode) operand2);

            return node;
        }

        throw new RuntimeException("Invalid condition expression");
    }

    //----------------------- Math -----------------------------


    @Override
    public Object visitMathematicalExpression(HTMLParser.MathematicalExpressionContext ctx) {
        Object operand1 = visit(ctx.expression(0));
        Object operand2 = visit(ctx.expression(1));

        // Check if the expressions is valid
        if (operand1 instanceof ValuableNode && operand2 instanceof ValuableNode) {
            MathematicalExpressionNode node = new MathematicalExpressionNode();

            // Check if the expression is a multiplicative
            if (ctx.MULTIPLICATIVE_OPERATOR() != null) {
                node.setOperator(ctx.MULTIPLICATIVE_OPERATOR().getText());
            } else if (ctx.ADDITIVE_OPERATOR() != null) { // Check if the expression is a additive
                node.setOperator(ctx.ADDITIVE_OPERATOR().getText());
            }

            // Set operands expressions
            node.setOperand1(((ValuableNode) operand1));
            node.setOperand2(((ValuableNode) operand2));

            return node;
        }

        throw new RuntimeException("Invalid Mathematical Expression");
    }


    //----------------------- CP Attributes --------------------------
    @Override
    public CPIFAttributeNode visitCpIF(HTMLParser.CpIFContext ctx) {
        CPIFAttributeNode cpIfAttribute = new CPIFAttributeNode();

        Object expressionResult = visit(ctx.expression());

        if (expressionResult instanceof LogicalNode) {
            cpIfAttribute.setName(ctx.CP_IF().getText());
            cpIfAttribute.setValue((LogicalNode) expressionResult);
        } else
            throw new RuntimeException("This isn't logical value");

        return cpIfAttribute;
    }

    @Override
    public CPShowAttributeNode visitCpSHOW(HTMLParser.CpSHOWContext ctx) {
        CPShowAttributeNode cpShowAttribute = new CPShowAttributeNode();

        Object expressionResult = visit(ctx.expression());

        if (expressionResult instanceof LogicalNode) {
            cpShowAttribute.setName(ctx.CP_SHOW().getText());
            cpShowAttribute.setValue((LogicalNode) expressionResult);
        } else
            throw new RuntimeException("This isn't logical value");

        return cpShowAttribute;
    }

    @Override
    public CPHideAttributeNode visitCpHIDE(HTMLParser.CpHIDEContext ctx) {
        CPHideAttributeNode cpHideAttribute = new CPHideAttributeNode();

        Object expressionResult = visit(ctx.expression());

        if (expressionResult instanceof LogicalNode) {
            cpHideAttribute.setName(ctx.CP_HIDE().getText());
            cpHideAttribute.setValue((LogicalNode) expressionResult);
        } else
            throw new RuntimeException("This isn't logical value");

        return cpHideAttribute;
    }

    @Override
    public CPModelAttributeNode visitCpMODEL(HTMLParser.CpMODELContext ctx) {
        Object expressionResult = visit(ctx.expression());

        if (expressionResult instanceof ValuableNode) {
            CPModelAttributeNode node = new CPModelAttributeNode();

            node.setName(ctx.CP_MODEL().getText());
            node.setValue((ValuableNode) expressionResult);

            return node;
        }

        throw new RuntimeException("Invalid model expression");
    }

    @Override
    public CPForAttributeNode.ForExpressionNode.ForIndexExpressionNode visitIndexExpression(HTMLParser.IndexExpressionContext ctx) {
        Object indexExpression = visit(ctx.expression(0));
        Object indexValueExpression = visit(ctx.expression(1));

        if (!(indexExpression instanceof VariableExpressionNode)
                || !(indexValueExpression instanceof ValuableNode)) {
            throw new RuntimeException("Invalid index expression");
        }

        CPForAttributeNode.ForExpressionNode.ForIndexExpressionNode node = new CPForAttributeNode.ForExpressionNode.ForIndexExpressionNode();
        node.setIndexName(((VariableExpressionNode) indexExpression).getVariableName());
        node.setIndexValue((ValuableNode) indexValueExpression);

        return node;
    }

    @Override
    public CPForAttributeNode.ForPairNode visitPairExpression(HTMLParser.PairExpressionContext ctx) {
        CPForAttributeNode.ForPairNode node = new CPForAttributeNode.ForPairNode();

        node.setKey(ctx.variableName(0).ANY_NAME().getText());
        node.setValue(ctx.variableName(1).ANY_NAME().getText());

        return node;
    }

    @Override
    public CPForAttributeNode.ForExpressionNode visitForInExpression(HTMLParser.ForInExpressionContext ctx) {
        CPForAttributeNode.ForExpressionNode node = new CPForAttributeNode.ForExpressionNode();
        if (ctx.pairExpression() == null) { // Iterator expression
            Object iteratorExpression = visit(ctx.expression(0));
            Object valueExpression = visit(ctx.expression(1));

            if (!(iteratorExpression instanceof VariableExpressionNode)) {
                throw new RuntimeException("Invalid iterator");
            }

            if (!(valueExpression instanceof IterableNode)) {
                throw new RuntimeException("Invalid value expression");
            }

            node.setVariableName(((VariableExpressionNode) iteratorExpression).getVariableName());
            node.setValue((IterableNode) valueExpression);


            // Visit index expression
            if (ctx.indexExpression() != null) {
                CPForAttributeNode.ForExpressionNode.ForIndexExpressionNode indexExpression = visitIndexExpression(ctx.indexExpression());
                node.setIndex(indexExpression);
            }

        } else { // Pair expression
            Object valueExpression = visit(ctx.expression(0));

            if (!(valueExpression instanceof IterableNode)) {
                throw new RuntimeException("Invalid value expression");
            }

            CPForAttributeNode.ForPairNode pairNode = visitPairExpression(ctx.pairExpression());

            node.setValue((IterableNode) valueExpression);
            node.setPair(pairNode);
        }

        return node;
    }

    @Override
    public CPForAttributeNode visitCpFOR(HTMLParser.CpFORContext ctx) {
        Object forInExpression = visit(ctx.forInExpression());

        // Check if the expression is valid;
        if (forInExpression instanceof CPForAttributeNode.ForExpressionNode) {
            CPForAttributeNode node = new CPForAttributeNode();

            node.setName(ctx.CP_FOR().getText());
            node.setValue((CPForAttributeNode.ForExpressionNode) forInExpression);

            return node;
        }

        throw new RuntimeException("Invalid for expression");
    }

    @Override
    public CPSwitchAttributeNode visitCpSWITCH(HTMLParser.CpSWITCHContext ctx) {
        Object expression = visit(ctx.expression());

        if (expression instanceof ValuableNode) {
            CPSwitchAttributeNode node = new CPSwitchAttributeNode();
            node.setName(ctx.CP_SWITCH().getText());
            node.setValue((ValuableNode) expression);

            return node;
        }

        throw new RuntimeException("Invalid switch expression");
    }

    @Override
    public CPSwitchCaseAttributeNode visitCpSWITCH_CASE(HTMLParser.CpSWITCH_CASEContext ctx) {
        Object expression = visit(ctx.expression());

        if (expression instanceof ValuableNode) {
            CPSwitchCaseAttributeNode node = new CPSwitchCaseAttributeNode();

            node.setName(ctx.CP_SWITCH_CASE().getText());
            node.setValue((ValuableNode) expression);

            return node;
        }

        throw new RuntimeException("Invalid switch expression");
    }

    @Override
    public CPSwitchDefaultAttributeNode visitCpSWITCH_DEFAULT(HTMLParser.CpSWITCH_DEFAULTContext ctx) {
        CPSwitchDefaultAttributeNode node = new CPSwitchDefaultAttributeNode();

        node.setName(ctx.CP_SWITCH_DEFAULT().getText());

        return node;
    }
}
