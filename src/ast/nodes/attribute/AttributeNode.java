package ast.nodes.attribute;

import ast.nodes.Node;

public abstract class AttributeNode<T> extends Node {
    protected String name;
    protected T value;

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(T value) {
        this.value = value;
    }


}
