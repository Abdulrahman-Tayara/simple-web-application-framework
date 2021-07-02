package ast.nodes.expression.value.literal;

import ast.nodes.expression.condition.LogicalNode;

/**
 * Literal Boolean value, ex: true, false
 */
public class BooleanValueNode extends LiteralExpressionNode<Boolean> implements LogicalNode {
    @Override
    public String toHtml() {
        return value.toString();
    }
}
