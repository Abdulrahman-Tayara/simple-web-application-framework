package ast.nodes.html;

import ast.nodes.attribute.AttributeNode;

import java.util.ArrayList;
import java.util.List;

public class HTMLTagNode extends HTMLElementNode {
    private String name;
    private List<AttributeNode<?>> attributes = new ArrayList<>();
    private HTMLContentNode htmlContentNode;

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

    public HTMLContentNode getHtmlContentNode() {
        return htmlContentNode;
    }

    public void setHtmlContentNode(HTMLContentNode htmlContentNode) {
        this.htmlContentNode = htmlContentNode;
    }

    @Override
    public String toString() {
        return "tag name: " + getName() + "\n \t" + "content: " + htmlContentNode;
    }

}

