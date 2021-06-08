package symantec_check;

import ast.nodes.Node;
import ast.nodes.attribute.AttributeNode;
import ast.nodes.attribute.Structural;
import ast.nodes.html.HTMLElementNode;
import ast.nodes.html.HTMLTagNode;
import ast.nodes.html.HtmlDocumentNode;

import java.util.ArrayList;
import java.util.List;

public class StructuralChecker {

    private List<Exception> exceptions = new ArrayList<>();

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void check(Node node) {
        if (node instanceof HtmlDocumentNode) {
            HtmlDocumentNode htmlDocNode = ((HtmlDocumentNode) node);
            for (HTMLElementNode elementNode : htmlDocNode.getElements()) {
                check(elementNode);
            }
        } else if (node instanceof HTMLTagNode) {
            HTMLTagNode tagNode = ((HTMLTagNode) node);

            int countOfStructuralNodes = 0;
            for (AttributeNode<?> attributeNode : tagNode.getAttributes()) {
                if(attributeNode instanceof Structural) {
                    countOfStructuralNodes++;
                }
                if(countOfStructuralNodes > 1) {
                    exceptions.add(new Exception("A tag can't have more than one structural attribute (\"cp-if, cp-if-else, cp-else, cp-show, cp-hide, cp-case, cp-for\")."));
                    break;
                }
            }

            for (HTMLElementNode elementNode : tagNode.getContent()) {
                check(elementNode);
            }
        }
    }

}
