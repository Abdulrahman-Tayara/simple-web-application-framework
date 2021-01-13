package ast.nodes.html;

public class HTMLTextNode extends HTMLElementNode {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text != null)
            this.text = text.trim();
    }
}
