package semantic_check;

import ast.nodes.Node;
import ast.nodes.attribute.AttributeNode;
import ast.nodes.attribute.HTMLAttributeNode;
import ast.nodes.html.HTMLTagNode;

import java.util.ArrayList;
import java.util.List;

public class AttributeChecker implements IChecker {

    List<Exception> exceptions = new ArrayList<>();

    private static final String IMAGE_TAG_NAME = "img";
    private static final String ANCHOR_TAG_NAME = "a";

    private static final String SRC_ATTR_NAME = "src";
    private static final String HREF_ATTR_NAME = "href";

    @Override
    public List<Exception> check(Node node) {
        exceptions.clear();
        if(node instanceof HTMLTagNode) {
            checkImageTag((HTMLTagNode) node);
            checkAnchorTag((HTMLTagNode) node);
        }
        return exceptions;
    }

    private void checkImageTag(HTMLTagNode node) {
        if (node.getName().equals(IMAGE_TAG_NAME)) {

            //check has href attribute
            boolean hasSrc = false;
            boolean hasValue = false;
            if (node.getAttributes() != null) {

                for (AttributeNode attr : node.getAttributes()) {
                    if (attr.getName().equals(SRC_ATTR_NAME)) {
                        hasSrc = true;
                        if (!((HTMLAttributeNode) attr).getValue().equals("\"\"")) {
                            hasValue = true;
                        }
                        break;
                    }
                }
            }

            if (!hasSrc) {
                this.exceptions.add(new Exception("does not have src"));
            } else if (!hasValue) {
                this.exceptions.add(new Exception("src does not have a value"));
            }
        }
    }

    private void checkAnchorTag(HTMLTagNode node) {
        if (node.getName().equals(ANCHOR_TAG_NAME)) {

            //check has href attribute
            boolean hasHref = false;
            boolean hasValue = false;
            if (node.getAttributes() != null) {

                for (AttributeNode attr : node.getAttributes()) {
                    if (attr.getName().equals(HREF_ATTR_NAME)) {
                        hasHref = true;
                        if (!((HTMLAttributeNode) attr).getValue().equals("\"\"")) {
                            hasValue = true;
                        }
                        break;
                    }
                }
            }

            if (!hasHref) {
                this.exceptions.add(new Exception("does not have href"));
            } else if (!hasValue) {
                this.exceptions.add(new Exception("href does not have a value"));
            }
        }
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }
}
