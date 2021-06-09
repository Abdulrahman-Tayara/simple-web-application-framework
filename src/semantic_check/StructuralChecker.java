package semantic_check;

import ast.nodes.Node;
import ast.nodes.attribute.AttributeNode;
import ast.nodes.attribute.Structural;
import ast.nodes.html.HTMLTagNode;

public class StructuralChecker extends Checker {


    @Override
    public void checkNode(Node node) {
        if (node instanceof HTMLTagNode) {
            HTMLTagNode tagNode = ((HTMLTagNode) node);

            int countOfStructuralNodes = 0;
            for (AttributeNode<?> attributeNode : tagNode.getAttributes()) {
                if (attributeNode instanceof Structural) {
                    countOfStructuralNodes++;
                }
                if (countOfStructuralNodes > 1) {
                    exceptions.add(new Exception("A tag can't have more than one structural attribute (\"cp-if, cp-if-else, cp-else, cp-show, cp-hide, cp-case, cp-for\")."));
                    break;
                }
            }
        }
    }

}
