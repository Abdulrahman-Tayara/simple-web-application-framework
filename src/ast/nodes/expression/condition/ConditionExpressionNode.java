package ast.nodes.expression.condition;

import ast.nodes.expression.value.ValueExpressionNode;

/**
 * Any expression defines a condition, ex: x > 2, !x, x > 2 && y < 3
 */
public abstract class ConditionExpressionNode extends ValueExpressionNode implements LogicalNode {
}
