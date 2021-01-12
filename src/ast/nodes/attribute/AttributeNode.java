package ast.nodes.attribute;

import ast.nodes.Node;

public abstract class AttributeNode<T> extends Node {
    private String name;
    private T value;

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
