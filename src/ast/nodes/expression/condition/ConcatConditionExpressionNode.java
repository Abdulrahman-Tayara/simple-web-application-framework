package ast.nodes.expression.condition;

/**
 * Condition concatenation, ex: !x && y > 3, x == 3 || y < f()
 */
public class ConcatConditionExpressionNode extends ConditionExpressionNode {
    private String operator;
    private ConditionExpressionNode condition1, condition2;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public ConditionExpressionNode getCondition1() {
        return condition1;
    }

    public void setCondition1(ConditionExpressionNode condition1) {
        this.condition1 = condition1;
    }

    public ConditionExpressionNode getCondition2() {
        return condition2;
    }

    public void setCondition2(ConditionExpressionNode condition2) {
        this.condition2 = condition2;
    }
}
