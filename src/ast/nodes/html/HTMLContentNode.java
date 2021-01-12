package ast.nodes.html;

import java.util.ArrayList;
import java.util.List;

public class HTMLContentNode {
    private List<HTMLElementNode> content = new ArrayList<HTMLElementNode>();
    private String textContent;

    public List<HTMLElementNode> getContent() {
        return content;
    }

    public void setContent(List<HTMLElementNode> content) {
        this.content = content;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
