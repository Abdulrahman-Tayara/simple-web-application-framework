package ast.nodes.expression.value.variable;

import ast.nodes.expression.condition.LogicalNode;
import ast.nodes.expression.value.*;
import com.google.gson.annotations.SerializedName;

/**
 * Indexed variable, ex: x[2]['s'][f()]
 */
public class IndexedVariableExpressionNode extends ValueExpressionNode
        implements IndexableNode, LogicalNode, ConcatableNode {

    @SerializedName("indexed variable")
    private IndexableNode variable;
    private IndexNode index;

    public IndexableNode getVariable() {
        return variable;
    }

    public void setVariable(IndexableNode variable) {
        this.variable = variable;
    }

    public IndexNode getIndex() {
        return index;
    }

    public void setIndex(IndexNode index) {
        this.index = index;
    }

    public static class IndexNode {
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
}
