package ast.nodes.html;

import ast.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class HtmlDocumentNode extends Node {
    private List<HTMLElementNode> elements = new ArrayList<HTMLElementNode>();

    public void setElements(List<HTMLElementNode> elements) {
        this.elements = elements;
    }

    public List<HTMLElementNode> getElements() {
        return elements;
    }
}
