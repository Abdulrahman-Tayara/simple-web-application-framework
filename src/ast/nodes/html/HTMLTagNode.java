package ast.nodes.html;

import ast.nodes.attribute.*;

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

    public boolean validAttributes() {
        // Check if there is only one if attribute
        long ifCount = attributes.stream().filter(attribute -> attribute instanceof CPIFAttributeNode
                || attribute instanceof CPElseIfAttributeNode
                || attribute instanceof CPElseAttributeNode).count();

        // Check if there is only one switch attribute
        long switchCount = attributes.stream().filter(attribute -> attribute instanceof CPSwitchAttributeNode
                || attribute instanceof CPSwitchCaseAttributeNode
                || attribute instanceof CPSwitchDefaultAttributeNode).count();

        return ifCount <= 1 && switchCount <= 1;
    }

    public boolean hasContent() {
        return content != null && !content.isEmpty();
    }

    @Override
    public String toHtml() {
        StringBuilder builder = new StringBuilder();
        builder.append("<")
                .append(name);
        attributes.forEach(attribute -> {
            builder.append(attribute.toHtml());
            if (attribute instanceof CPAttributeNode) return;
            builder.append(" ");
        });
        builder.append(">");
        content.forEach(elementNode -> builder.append(elementNode.toHtml()));
        builder.append("</")
                .append(name)
                .append(">");
        return builder.toString();
    }
}

