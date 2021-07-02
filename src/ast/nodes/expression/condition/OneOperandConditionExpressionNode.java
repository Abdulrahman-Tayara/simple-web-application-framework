package ast.nodes.expression.condition;

/**
 * Condition with one operand, ex: !x, !f(), !true, !false
 */
public class OneOperandConditionExpressionNode extends ConditionExpressionNode {
    private String operator;
    private LogicalNode operand;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public LogicalNode getOperand() {
        return operand;
    }

    public void setOperand(LogicalNode operand) {
        this.operand = operand;
    }

    @Override
    public String toHtml() {
        return operator + operand.toHtml();
    }
}
