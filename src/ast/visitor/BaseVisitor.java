package ast.visitor;


import ast.nodes.attribute.*;
import ast.nodes.expression.condition.ConditionExpressionNode;
import ast.nodes.expression.condition.OneOperandConditionExpressionNode;
import ast.nodes.expression.math.MathematicalExpressionNode;
import ast.nodes.expression.value.ValuableNode;
import ast.nodes.expression.value.ValueExpressionNode;
import ast.nodes.expression.value.literal.ArrayNode;
import ast.nodes.expression.value.literal.BooleanValueNode;
import ast.nodes.expression.value.literal.NumericValueNode;
import ast.nodes.expression.value.literal.StringValueNode;
import ast.nodes.expression.value.variable.ConcatVariableExpressionNode;
import ast.nodes.expression.value.variable.FunctionExpressionNode;
import ast.nodes.html.HTMLContentNode;
import ast.nodes.html.HTMLElementNode;
import ast.nodes.html.HTMLTagNode;
import ast.nodes.html.HtmlDocumentNode;
import ast.nodes.expression.condition.LogicalNode;
import ast.nodes.expression.value.variable.VariableExpressionNode;
import gen.HTMLParser;
import gen.HTMLParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;
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
    public Object visitScriptletOrSeaWs(HTMLParser.ScriptletOrSeaWsContext ctx) {
        return super.visitScriptletOrSeaWs(ctx);
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
    public String visitHtmlChardata(HTMLParser.HtmlChardataContext ctx) {
        StringBuilder htmlText = new StringBuilder();

        if (ctx.SEA_WS() != null)
            htmlText.append(ctx.SEA_WS().getText());

        if (ctx.HTML_TEXT() != null)
            htmlText.append(ctx.HTML_TEXT().getText());

        ctx.htmlChardata().forEach(htmlChardataContext -> htmlText.append(visitHtmlChardata(htmlChardataContext)));

        return htmlText.toString();
    }

    @Override
    public HTMLContentNode visitHtmlContent(HTMLParser.HtmlContentContext ctx) {
        HTMLContentNode contentNode = new HTMLContentNode();

        List<HTMLElementNode> elements = new ArrayList<>();
        StringBuilder textContent = new StringBuilder();


        int i = 0;
        if (ctx.htmlChardata(0) != null) { // First text content
            i = 1;
            textContent.append(visitHtmlChardata(ctx.htmlChardata(0)));
        }

        for (; i < ctx.children.size(); i++) {
            // If the content is an element
            if (ctx.getChild(i) instanceof HTMLParser.HtmlElementContext)
                elements.add(visitHtmlElement((HTMLParser.HtmlElementContext) ctx.getChild(i)));

                // If the content is a text
            else if (ctx.getChild(i) instanceof HTMLParser.HtmlChardataContext)
                textContent.append(visitHtmlChardata((HTMLParser.HtmlChardataContext) ctx.getChild(i)));
        }

        contentNode.setTextContent(textContent.toString());
        contentNode.setContent(elements);

        return contentNode;
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
                tag.setHtmlContentNode(visitHtmlContent(ctx.htmlContent()));

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
        List<ValueExpressionNode> values = new ArrayList<>();

        // Visit all nested expressions
        ctx.expression().forEach(expressionContext -> {
            Object visitResult = visit(expressionContext);

            if (visitResult instanceof ValueExpressionNode && !(visitResult instanceof ConditionExpressionNode)) {
                // Check if the result is also a ConcatVariableExpressionNode to add its values
                if (visitResult instanceof ConcatVariableExpressionNode)
                    values.addAll(((ConcatVariableExpressionNode) visitResult).getValues());
                    // Terminal object
                else values.add((ValueExpressionNode) visitResult);
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
        node.setOperator(ctx.CONDITIONAL_OPERATORS_ONE_OPERAND().getText());

        Object operand = visit(ctx.expression());

        if (operand instanceof LogicalNode) {
            node.setOperand((LogicalNode) operand);

            return node;
        }

        throw new RuntimeException(ctx.expression().getText() + " isn't logical");
    }

    //----------------------- Math -----------------------------


    @Override
    public Object visitMathematicalExpression(HTMLParser.MathematicalExpressionContext ctx) {
        try {
            MathematicalExpressionNode node = new MathematicalExpressionNode();
            if (ctx.MULTIPLICATIVE_OPERATOR() != null) {
                node.setOperator(ctx.MULTIPLICATIVE_OPERATOR().getText());
            } else if(ctx.ADDITIVE_OPERATOR() != null) {
                node.setOperator(ctx.ADDITIVE_OPERATOR().getText());
            }
            node.setOperand1(((ValuableNode) visit(ctx.expression().get(0))));
            node.setOperand2(((ValuableNode) visit(ctx.expression().get(1))));
            return node;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid Mathematical Expression");
        }
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
    public ObjectLiteralNode visitLiteralObjectExpression(HTMLParser.LiteralObjectExpressionContext ctx) {


        return super.visitLiteralObjectExpression(ctx);
    }
}
