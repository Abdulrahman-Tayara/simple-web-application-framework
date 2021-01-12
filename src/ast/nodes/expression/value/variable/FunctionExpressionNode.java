package ast.nodes.expression.value.variable;

import ast.nodes.expression.ExpressionNode;
import ast.nodes.expression.condition.LogicalNode;
import ast.nodes.expression.value.IndexableNode;
import ast.nodes.expression.value.ValuableNode;
import ast.nodes.expression.value.ValueExpressionNode;

import java.util.List;

/**
 * Function expression, ex: f(1, true, [1, 2]), f()
 */
public class FunctionExpressionNode extends ValueExpressionNode implements IndexableNode, LogicalNode {

    private String functionName;
    private List<FunctionParam> params;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public List<FunctionParam> getParams() {
        return params;
    }

    public void setParams(List<FunctionParam> params) {
        this.params = params;
    }


    public static class FunctionParam {
        private ValuableNode value;

        public FunctionParam(ValuableNode value) {
            this.value = value;
        }

        public ValuableNode getValue() {
            return value;
        }

        public void setValue(ValuableNode value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(functionName).append('(');

        if (params != null && params.size() > 0) {
            params.forEach(param -> builder.append(param).append(','));

            builder.deleteCharAt(builder.length() - 1); // Delete last comma.
        }

        builder.append(')');
        return builder.toString();
    }
}
