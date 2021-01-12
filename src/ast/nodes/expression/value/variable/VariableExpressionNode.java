package ast.nodes.expression.value.variable;

import ast.nodes.expression.condition.LogicalNode;
import ast.nodes.expression.value.ConcatableNode;
import ast.nodes.expression.value.IndexableNode;
import ast.nodes.expression.value.ValueExpressionNode;

/**
 * Simple Variable name, ex: x, y, anyName
 */
public class VariableExpressionNode extends ValueExpressionNode implements IndexableNode, LogicalNode, ConcatableNode {
    private String variableName;

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
}
