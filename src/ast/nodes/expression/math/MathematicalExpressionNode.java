package ast.nodes.expression.math;

import ast.nodes.expression.value.ValuableNode;
import ast.nodes.expression.value.ValueExpressionNode;

public class MathematicalExpressionNode extends ValueExpressionNode {

    private String operator;
    private ValuableNode operand1, operand2;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public ValuableNode getOperand1() {
        return operand1;
    }

    public void setOperand1(ValuableNode operand1) {
        this.operand1 = operand1;
    }

    public ValuableNode getOperand2() {
        return operand2;
    }

    public void setOperand2(ValuableNode operand2) {
        this.operand2 = operand2;
    }

    @Override
    public String toHtml() {
        return operand1.toHtml() + ' ' + operator + ' ' + operand2.toHtml();
    }
}
