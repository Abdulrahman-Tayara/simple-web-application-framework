package ast.nodes.attribute;

public class HTMLAttributeNode extends AttributeNode<String> {

    public HTMLAttributeNode() {
    }

    public HTMLAttributeNode(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
