package ast.nodes.expression.value.variable;

import ast.nodes.expression.condition.LogicalNode;
import ast.nodes.expression.value.ConcatableNode;
import ast.nodes.expression.value.IndexableNode;
import ast.nodes.expression.value.ValueExpressionNode;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Contact variable, ex: x.y.f()
 */
public class ConcatVariableExpressionNode extends ValueExpressionNode
        implements IndexableNode, LogicalNode, ConcatableNode {

    @SerializedName("concatenate values")
    private List<ConcatableNode> values;

    public List<ConcatableNode> getValues() {
        return values;
    }

    public void setValues(List<ConcatableNode> values) {
        this.values = values;
    }
}
