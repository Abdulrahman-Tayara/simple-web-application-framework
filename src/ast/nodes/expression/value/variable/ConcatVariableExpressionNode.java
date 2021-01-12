package ast.nodes.expression.value.variable;

import ast.nodes.expression.condition.LogicalNode;
import ast.nodes.expression.value.IndexableNode;
import ast.nodes.expression.value.ValueExpressionNode;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Contact variable, ex: x.y.f()
 */
public class ConcatVariableExpressionNode extends ValueExpressionNode implements IndexableNode, LogicalNode {
    @SerializedName("concatenate values")
    private List<ValueExpressionNode> values;

    public List<ValueExpressionNode> getValues() {
        return values;
    }

    public void setValues(List<ValueExpressionNode> values) {
        this.values = values;
    }
}
