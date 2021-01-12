package ast.nodes.expression.value;

import ast.nodes.expression.condition.ConditionExpressionNode;

/**
 * Conditional expression, ex: condition ? expression : expression
 */
public class ConditionalExpressionNode extends ValueExpressionNode {
    private ConditionExpressionNode conditionExpressionNode;
    private ValuableNode expression1, expression2;

    public ConditionExpressionNode getConditionExpression() {
        return conditionExpressionNode;
    }

    public void setConditionExpression(ConditionExpressionNode conditionExpressionNode) {
        this.conditionExpressionNode = conditionExpressionNode;
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
