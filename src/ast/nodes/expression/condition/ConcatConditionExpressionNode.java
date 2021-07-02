package ast.nodes.expression.condition;

/**
 * Condition concatenation, ex: !x && y > 3, x == 3 || y < f()
 */
public class ConcatConditionExpressionNode extends ConditionExpressionNode {
    private String operator;
    private LogicalNode condition1, condition2;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public LogicalNode getCondition1() {
        return condition1;
    }

    public void setCondition1(LogicalNode condition1) {
        this.condition1 = condition1;
    }

    public LogicalNode getCondition2() {
        return condition2;
    }

    public void setCondition2(LogicalNode condition2) {
        this.condition2 = condition2;
    }

    @Override
    public String toHtml() {
        return condition1.toHtml() + ' ' + operator + ' ' + condition2;
    }
}
