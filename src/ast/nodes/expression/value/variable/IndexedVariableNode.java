package ast.nodes.expression.value.variable;

import ast.nodes.expression.condition.LogicalNode;
import ast.nodes.expression.value.IndexableNode;
import ast.nodes.expression.value.ValuableNode;
import ast.nodes.expression.value.ValueExpressionNode;

import java.util.List;

/**
 * Indexed variable, ex: x[2]['s'][f()]
 */
public class IndexedVariableNode extends ValueExpressionNode implements IndexableNode, LogicalNode {
    private IndexableNode variable;
    private List<Index> indices;

    public IndexableNode getVariable() {
        return variable;
    }

    public void setVariable(IndexableNode variable) {
        this.variable = variable;
    }

    public List<Index> getIndices() {
        return indices;
    }

    public void setIndices(List<Index> indices) {
        this.indices = indices;
    }

    public static class Index {
        private ValuableNode value;

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

        builder.append(variable).append('[');

        if (indices != null && indices.size() > 0) {
            indices.forEach(index -> builder.append(index).append(','));

            builder.deleteCharAt(builder.length() - 1); // Delete last comma.
        }

        builder.append(']');
        return builder.toString();
    }
}
