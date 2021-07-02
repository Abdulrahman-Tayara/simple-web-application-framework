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

    public boolean hasContent() {
        return content != null && !content.isEmpty();
    }

    public String getId() {
        for (AttributeNode<?> attributeNode : getAttributes()) {
            if (attributeNode.getName().equals("id")) {
                return ((HTMLAttributeNode) attributeNode).getValue();
            }
        }
        return null;
    }

    public AttributeNode<?> getAttribute(int index) {
        return attributes.get(index);
    }

    public int indexOf(Class<? extends AttributeNode<?>> attribute) {
        for (int i = 0; i < attributes.size(); i++) {
            if (attributes.get(i).getClass().isAssignableFrom(attribute)) {
                return i;
            }
        }

        return -1;
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
        if (content != null && !content.isEmpty())
            content.forEach(elementNode -> builder.append(elementNode.toHtml()));
        builder.append("</")
                .append(name)
                .append(">");
        return builder.toString();
    }
}

