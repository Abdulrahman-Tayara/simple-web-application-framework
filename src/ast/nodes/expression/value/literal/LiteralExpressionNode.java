package ast.nodes.expression.value.literal;


import ast.nodes.expression.value.ValueExpressionNode;

public abstract class LiteralExpressionNode<T> extends ValueExpressionNode {
    protected T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
