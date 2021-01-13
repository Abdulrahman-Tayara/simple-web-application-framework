package ast.nodes.expression.value.literal;

import ast.nodes.expression.value.IndexableNode;
import ast.nodes.expression.value.IterableNode;

/**
 * Literal String Value, ex: 'test', 's'
 */
public class StringValueNode extends LiteralExpressionNode<String>
        implements IndexableNode, IterableNode {

}
