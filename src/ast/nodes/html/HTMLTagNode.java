package ast.nodes.html;

import ast.nodes.attribute.AttributeNode;

import java.util.ArrayList;
import java.util.List;

public class HTMLTagNode extends HTMLElementNode {
    private String name;
    private List<AttributeNode<?>> attributes = new ArrayList<>();
    private List<HTMLElementNode> content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AttributeNode<?>> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeNode<?>> attributes) {
        this.attributes = attributes;
    }

    public List<HTMLElementNode> getContent() {
        return content;
    }

    public void setContent(List<HTMLElementNode> content) {
        this.content = content;
    }
}

