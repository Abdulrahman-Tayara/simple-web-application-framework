package ast.visitor;


import ast.nodes.Node;
import ast.nodes.attribute.*;
import ast.nodes.expression.ExpressionNode;
import ast.nodes.expression.PipeExpressionNode;
import ast.nodes.expression.condition.*;
import ast.nodes.attribute.EventAttributeNode;
import ast.nodes.expression.math.MathematicalExpressionNode;
import ast.nodes.expression.value.*;
import ast.nodes.expression.value.literal.*;
import ast.nodes.expression.value.variable.ConcatVariableExpressionNode;
import ast.nodes.expression.value.variable.FunctionExpressionNode;
import ast.nodes.expression.value.variable.IndexedVariableExpressionNode;
import ast.nodes.html.*;
import ast.nodes.expression.value.variable.VariableExpressionNode;
import gen.HTMLParser;
import gen.HTMLParserBaseVisitor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class BaseVisitor extends HTMLParserBaseVisitor {

    private final Path eventFilePath = Paths.get(new File(EventAttributeNode.EVENTS_FILE_DIR).toURI());

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
    public VariableScopeExpressionNode visitVariableScopeContent(HTMLParser.VariableScopeContentContext ctx) {
        Object scopeExpression = visit(ctx.expression());

        if (!(scopeExpression instanceof ExpressionNode))
            throw new RuntimeException("Invalid scope expression");

        VariableScopeExpressionNode node = new VariableScopeExpressionNode();
        node.setLine(ctx.VARIABLE_SCOPE_OPEN().getSymbol().getLine());
        node.setCol(ctx.VARIABLE_SCOPE_OPEN().getSymbol().getCharPositionInLine());

        node.setScopeExpression((ExpressionNode) scopeExpression);

        return node;
    }

    @Override
    public HTMLTextNode visitHtmlChardata(HTMLParser.HtmlChardataContext ctx) {
        HTMLTextNode node = new HTMLTextNode();

        StringBuilder htmlText = new StringBuilder();

        if (ctx.SEA_WS() != null) {
            node.setLine(ctx.SEA_WS().getSymbol().getLine());
            node.setCol(ctx.SEA_WS().getSymbol().getCharPositionInLine());
            htmlText.append(ctx.SEA_WS().getText());
        }

        if (ctx.HTML_TEXT() != null) {
            node.setLine(ctx.HTML_TEXT().getSymbol().getLine());
            node.setCol(ctx.HTML_TEXT().getSymbol().getCharPositionInLine());
            htmlText.append(ctx.HTML_TEXT().getText());
        }

        node.setText(htmlText.toString());

        return node;
    }

    @Override
    public List<HTMLElementNode> visitHtmlContent(HTMLParser.HtmlContentContext ctx) {
        if (ctx.children == null)
            return Collections.emptyList();

        List<HTMLElementNode> elements =
                // Iterate over children
                ctx.children.stream().filter( // Filter the contexts to
                        child -> child instanceof HTMLParser.HtmlElementContext // HTMLElement
                                || child instanceof HTMLParser.HtmlChardataContext // HTMLText
                                || child instanceof HTMLParser.VariableScopeContentContext // Variable Scope
                ).map(context -> (HTMLElementNode) visit(context)) // Visit the filtered contexts
                        .collect(Collectors.toList());

        return elements;
    }

    @Override
    public HTMLScriptNode visitScript(HTMLParser.ScriptContext ctx) {
        HTMLScriptNode node = new HTMLScriptNode();
        node.setLine(ctx.SCRIPT_OPEN().getSymbol().getLine());
        node.setLine(ctx.SCRIPT_OPEN().getSymbol().getCharPositionInLine());

        if (ctx.SCRIPT_BODY() != null)
            node.setScriptBody(ctx.SCRIPT_BODY().getText());
        else if (ctx.SCRIPT_SHORT_BODY() != null)
            node.setScriptBody(ctx.SCRIPT_SHORT_BODY().getText());

        return node;
    }

    @Override
    public HTMLElementNode visitHtmlElement(HTMLParser.HtmlElementContext ctx) {
        if (ctx.TAG_OPEN(0) != null) { // Tag

            // Check if the opened tag name not equals the closed tag name
            if (ctx.TAG_NAME(1) != null && !ctx.TAG_NAME(0).getText().equals(ctx.TAG_NAME(1).getText())) {
                throw new RuntimeException("The closed tag name isn't the same opened tag name");
            }

            HTMLTagNode tag = new HTMLTagNode();
            tag.setLine(ctx.TAG_OPEN(0).getSymbol().getLine());
            tag.setCol(ctx.TAG_OPEN(0).getSymbol().getCharPositionInLine());

            // Set Tag Name
            tag.setName(ctx.TAG_NAME(0).getText());

            // Add tag attributes ex: <Tag attribute1, attribute2, ... > ... </Tag>
            ctx.htmlAttribute().forEach(attribute -> {
                tag.getAttributes().add(visitHtmlAttribute(attribute));
            });

            if (!tag.validAttributes())
                throw new RuntimeException("Invalid tag attributes");

            if (ctx.htmlContent() != null)
                tag.setContent(visitHtmlContent(ctx.htmlContent()));

            return tag;
        } else if (ctx.script() != null) { // Script
            return visitScript(ctx.script());
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
            attribute.setLine(ctx.ATTVALUE_VALUE().getSymbol().getLine());
            attribute.setCol(ctx.ATTVALUE_VALUE().getSymbol().getCharPositionInLine());

            attribute.setName(ctx.TAG_NAME().getText()); // Add name
            if (ctx.ATTVALUE_VALUE() != null) {
                attribute.setValue(ctx.ATTVALUE_VALUE().getText()); // Add value
            } else {
                attribute.setValue(null);
            }

            return attribute;
        }

        // CP attribute
        return (AttributeNode<?>) super.visitHtmlAttribute(ctx);
    }

    @Override
    public Object visitParenthesizedExpression(HTMLParser.ParenthesizedExpressionContext ctx) {
        return visit(ctx.expression());
    }

    //----------------------- Pipe -----------------------------

    @Override
    public PipeExpressionNode visitPipeExpression(HTMLParser.PipeExpressionContext ctx) {
        Object pipedExpression = visit(ctx.expression(0));
        Object functionExpression = visit(ctx.expression(1));

        // Visit piped expression
        if (!(pipedExpression instanceof ExpressionNode))
            throw new RuntimeException("Invalid pipe expression");

        // Check function expression
        if (!(functionExpression instanceof FunctionExpressionNode
                || functionExpression instanceof VariableExpressionNode))
            throw new RuntimeException("Invalid pipe expression");


        FunctionExpressionNode functionNode;

        // If the function is just a variableName
        if (functionExpression instanceof VariableExpressionNode) {
            functionNode = new FunctionExpressionNode();
            functionNode.setLine(((VariableExpressionNode) functionExpression).getLine());
            functionNode.setCol(((VariableExpressionNode) functionExpression).getCol());
            functionNode.setFunctionName(((VariableExpressionNode) functionExpression).getVariableName());
            functionNode.setParams(Collections.emptyList());
        } else // If the function is full function expression
            functionNode = (FunctionExpressionNode) functionExpression;

        PipeExpressionNode node = new PipeExpressionNode();
        node.setLine(((ExpressionNode) pipedExpression).getLine());
        node.setCol(((ExpressionNode) pipedExpression).getCol());

        node.setPipedData((ExpressionNode) pipedExpression);
        node.setTransformer(functionNode);

        return node;
    }


    //----------------------- Literal Values -----------------------------

    @Override
    public BooleanValueNode visitLiteralBooleanExpression(HTMLParser.LiteralBooleanExpressionContext ctx) {
        BooleanValueNode node = new BooleanValueNode();
        node.setLine(ctx.literalBooleanValue().BOOLEAN_VALUE().getSymbol().getLine());
        node.setCol(ctx.literalBooleanValue().BOOLEAN_VALUE().getSymbol().getCharPositionInLine());
        node.setValue(Boolean.valueOf(ctx.literalBooleanValue().BOOLEAN_VALUE().getText().toLowerCase()));
        return node;
    }

    @Override
    public StringValueNode visitLiteralStringExpression(HTMLParser.LiteralStringExpressionContext ctx) {
        StringValueNode node = new StringValueNode();
        node.setLine(ctx.literalStringValue().STRING_VALUE().getSymbol().getLine());
        node.setCol(ctx.literalStringValue().STRING_VALUE().getSymbol().getCharPositionInLine());
        node.setValue(ctx.literalStringValue().STRING_VALUE().getText().replace("'", ""));
        return node;
    }

    @Override
    public NumericValueNode visitLiteralNumericExpression(HTMLParser.LiteralNumericExpressionContext ctx) {
        NumericValueNode node = new NumericValueNode();
        node.setLine(ctx.literalNumericValue().NUMERIC_VALUE().getSymbol().getLine());
        node.setCol(ctx.literalNumericValue().NUMERIC_VALUE().getSymbol().getCharPositionInLine());
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
        node.setLine(ctx.ARRAY_OPEN().getSymbol().getLine());
        node.setCol(ctx.ARRAY_OPEN().getSymbol().getCharPositionInLine());

        // Visit all items and put them.
        node.setValue(ctx.arrayItemValue().stream().map(this::visitArrayItemValue).collect(Collectors.toList()));

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


    //----------------------- Variable -----------------------------

    @Override
    public VariableExpressionNode visitVariableNameExpression(HTMLParser.VariableNameExpressionContext ctx) {
        VariableExpressionNode node = new VariableExpressionNode();
        node.setLine(ctx.variableName().ANY_NAME().getSymbol().getLine());
        node.setCol(ctx.variableName().ANY_NAME().getSymbol().getCharPositionInLine());
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
    public FunctionExpressionNode visitFunctionCallExpression(HTMLParser.FunctionCallExpressionContext ctx) {
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


    //----------------------- Condition -----------------------------

    @Override
    public OneOperandConditionExpressionNode visitOneOperandConditionExpression(HTMLParser.OneOperandConditionExpressionContext ctx) {
        OneOperandConditionExpressionNode node = new OneOperandConditionExpressionNode();
        node.setLine(ctx.CONDITIONAL_OPERATORS_ONE_OPERAND().getSymbol().getLine());
        node.setCol(ctx.CONDITIONAL_OPERATORS_ONE_OPERAND().getSymbol().getCharPositionInLine());

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

    @Override
    public ConditionalExpressionNode visitConditionalExpression(HTMLParser.ConditionalExpressionContext ctx) {
        Object conditionExpression = visit(ctx.expression(0));

        if (!(conditionExpression instanceof LogicalNode))
            throw new RuntimeException("Invalid conditional expression");

        Object value1Expression = visit(ctx.expression(1));
        Object value2Expression = visit(ctx.expression(2));

        if (!(value1Expression instanceof ValuableNode)
                || !(value2Expression instanceof ValuableNode))
            throw new RuntimeException("Invalid conditional expression");

        ConditionalExpressionNode node = new ConditionalExpressionNode();

        node.setConditionExpression((LogicalNode) conditionExpression);
        node.setExpression1((ValuableNode) value1Expression);
        node.setExpression2((ValuableNode) value2Expression);

        return node;
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
    public CPAppAttributeNode visitCpApp(HTMLParser.CpAppContext ctx) {
        CPAppAttributeNode node = new CPAppAttributeNode();
        node.setLine(ctx.CP_APP().getSymbol().getLine());
        node.setCol(ctx.CP_APP().getSymbol().getCharPositionInLine());
        node.setName(ctx.CP_APP().getText());
        node.setValue(ctx.ANY_NAME().getText());
        return node;
    }

    @Override
    public CPIFAttributeNode visitCpIF(HTMLParser.CpIFContext ctx) {
        CPIFAttributeNode cpIfAttribute = new CPIFAttributeNode();
        cpIfAttribute.setLine(ctx.CP_IF().getSymbol().getLine());
        cpIfAttribute.setCol(ctx.CP_IF().getSymbol().getCharPositionInLine());

        Object expressionResult = visit(ctx.expression());

        if (expressionResult instanceof LogicalNode) {
            cpIfAttribute.setName(ctx.CP_IF().getText());
            cpIfAttribute.setValue((LogicalNode) expressionResult);
        } else
            throw new RuntimeException("This isn't logical value");

        return cpIfAttribute;
    }

    @Override
    public CPElseIfAttributeNode visitCpElseIf(HTMLParser.CpElseIfContext ctx) {
        CPElseIfAttributeNode node = new CPElseIfAttributeNode();
        node.setLine(ctx.CP_ELSE_IF().getSymbol().getLine());
        node.setCol(ctx.CP_ELSE_IF().getSymbol().getCharPositionInLine());

        Object expressionResult = visit(ctx.expression());

        if (expressionResult instanceof LogicalNode) {
            node.setName(ctx.CP_ELSE_IF().getText());
            node.setValue((LogicalNode) expressionResult);
        } else
            throw new RuntimeException("This isn't logical value");

        return node;
    }

    @Override
    public CPElseAttributeNode visitCpElse(HTMLParser.CpElseContext ctx) {
        CPElseAttributeNode node = new CPElseAttributeNode();
        node.setLine(ctx.CP_ELSE().getSymbol().getLine());
        node.setCol(ctx.CP_ELSE().getSymbol().getCharPositionInLine());

        Object expressionResult = visit(ctx.expression());

        if (expressionResult instanceof LogicalNode) {
            node.setName(ctx.CP_ELSE().getText());
            node.setValue((LogicalNode) expressionResult);
        } else
            throw new RuntimeException("This isn't logical value");

        return node;
    }

    @Override
    public CPShowAttributeNode visitCpSHOW(HTMLParser.CpSHOWContext ctx) {
        CPShowAttributeNode cpShowAttribute = new CPShowAttributeNode();
        cpShowAttribute.setLine(ctx.CP_SHOW().getSymbol().getLine());
        cpShowAttribute.setCol(ctx.CP_SHOW().getSymbol().getCharPositionInLine());

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
        cpHideAttribute.setLine(ctx.CP_HIDE().getSymbol().getLine());
        cpHideAttribute.setCol(ctx.CP_HIDE().getSymbol().getCharPositionInLine());

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
            node.setLine(ctx.CP_MODEL().getSymbol().getLine());
            node.setCol(ctx.CP_MODEL().getSymbol().getCharPositionInLine());

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

            if (!(valueExpression instanceof IndexableNode)) {
                throw new RuntimeException("Invalid value expression");
            }

            node.setVariableName(((VariableExpressionNode) iteratorExpression).getVariableName());
            node.setValue((IndexableNode) valueExpression);


            // Visit index expression
            if (ctx.indexExpression() != null) {
                CPForAttributeNode.ForExpressionNode.ForIndexExpressionNode indexExpression = visitIndexExpression(ctx.indexExpression());
                node.setIndex(indexExpression);
            }

        } else { // Pair expression
            Object valueExpression = visit(ctx.expression(0));

            if (!(valueExpression instanceof IndexableNode)) {
                throw new RuntimeException("Invalid value expression");
            }

            CPForAttributeNode.ForPairNode pairNode = visitPairExpression(ctx.pairExpression());

            node.setValue((IndexableNode) valueExpression);
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

            node.setLine(ctx.CP_FOR().getSymbol().getLine());
            node.setCol(ctx.CP_FOR().getSymbol().getCharPositionInLine());

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
            node.setLine(ctx.CP_SWITCH().getSymbol().getLine());
            node.setCol(ctx.CP_SWITCH().getSymbol().getCharPositionInLine());
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
            node.setLine(ctx.CP_SWITCH_CASE().getSymbol().getLine());
            node.setCol(ctx.CP_SWITCH_CASE().getSymbol().getCharPositionInLine());

            node.setName(ctx.CP_SWITCH_CASE().getText());
            node.setValue((ValuableNode) expression);

            return node;
        }

        throw new RuntimeException("Invalid switch expression");
    }

    @Override
    public CPSwitchDefaultAttributeNode visitCpSWITCH_DEFAULT(HTMLParser.CpSWITCH_DEFAULTContext ctx) {
        CPSwitchDefaultAttributeNode node = new CPSwitchDefaultAttributeNode();
        node.setLine(ctx.CP_SWITCH_DEFAULT().getSymbol().getLine());
        node.setCol(ctx.CP_SWITCH_DEFAULT().getSymbol().getCharPositionInLine());

        node.setName(ctx.CP_SWITCH_DEFAULT().getText());

        return node;
    }

    @Override
    public EventAttributeNode visitEvent(HTMLParser.EventContext ctx) {
        Object functionExpression = visit(ctx.expression());

        if (!(functionExpression instanceof FunctionExpressionNode))
            throw new RuntimeException("Invalid event expression");

        EventAttributeNode node = new EventAttributeNode();
        node.setLine(ctx.eventName().ANY_NAME().getSymbol().getLine());
        node.setCol(ctx.eventName().ANY_NAME().getSymbol().getCharPositionInLine());

        node.setName(visitEventName(ctx.eventName()));
        node.setValue((FunctionExpressionNode) functionExpression);

        return node;
    }

    @Override
    public String visitEventName(HTMLParser.EventNameContext ctx) {
        String eventName = ctx.ANY_NAME().getText();
//        try {
//            boolean exists = Files.readAllLines(eventFilePath).contains(eventName);
//
//            if (!exists)
//                throw new RuntimeException("Invalid event name");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return eventName;
    }
}
