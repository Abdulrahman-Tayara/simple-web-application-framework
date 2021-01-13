package ast.nodes.html;

import java.util.ArrayList;
import java.util.List;

public class HTMLContentNode {
    private List<HTMLElementNode> content = new ArrayList<HTMLElementNode>();

    public List<HTMLElementNode> getContent() {
        return content;
    }

    public void setContent(List<HTMLElementNode> content) {
        this.content = content;
    }
}
