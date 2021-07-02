package ast.nodes.expression.value.variable;

import ast.nodes.expression.ExpressionNode;
import ast.nodes.expression.condition.LogicalNode;
import ast.nodes.expression.value.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Function expression, ex: f(1, true, [1, 2]), f()
 */
public class FunctionExpressionNode extends ValueExpressionNode
        implements IndexableNode, LogicalNode, ConcatableNode {

    private String functionName;
    private ArrayList<FunctionParam> params;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public List<FunctionParam> getParams() {
        return params;
    }

    public void setParams(ArrayList<FunctionParam> params) {
        this.params = params;
    }

    public void addParamInFirst(FunctionParam param) {
        if (this.params == null)
            this.params = new ArrayList<>();

        this.params.add(0, param);
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

    @Override
    public String toHtml() {
        StringBuilder builder = new StringBuilder();
        params.forEach(param -> builder.append(param.value.toHtml()).append(','));
        if (builder.length() > 0)
            builder.setLength(builder.length() - 1);
        return functionName + '(' + builder.toString() + ')';
    }
}
