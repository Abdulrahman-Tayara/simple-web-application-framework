package ast.nodes.expression.value;


import ast.nodes.expression.condition.LogicalNode;

/**
 * Conditional expression, ex: condition ? expression : expression
 */
public class ConditionalExpressionNode extends ValueExpressionNode implements ConcatableNode, LogicalNode, IndexableNode {

    private LogicalNode condition;
    private ValuableNode expression1, expression2;

    public LogicalNode getConditionExpression() {
        return condition;
    }

    public void setConditionExpression(LogicalNode conditionExpressionNode) {
        this.condition = conditionExpressionNode;
    }

    public ValuableNode getExpression1() {
        return expression1;
    }

    public void setExpression1(ValuableNode expression1) {
        this.expression1 = expression1;
    }

    public ValuableNode getExpression2() {
        return expression2;
    }

    public void setExpression2(ValuableNode expression2) {
        this.expression2 = expression2;
    }
}
