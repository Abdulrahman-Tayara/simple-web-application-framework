package semantic_check;

import ast.nodes.Node;
import ast.nodes.attribute.AttributeNode;
import ast.nodes.attribute.Structural;
import ast.nodes.html.HTMLTagNode;
import semantic_check.exceptions.attributes.CpStructuralViolationException;

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
                    exceptions.add(new CpStructuralViolationException(attributeNode.getLine(), attributeNode.getCol()));
                    break;
                }
            }
        }
    }

}
