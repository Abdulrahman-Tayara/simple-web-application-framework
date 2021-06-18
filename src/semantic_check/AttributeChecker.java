package semantic_check;

import ast.nodes.Node;
import ast.nodes.attribute.AttributeNode;
import ast.nodes.attribute.HTMLAttributeNode;
import ast.nodes.html.HTMLTagNode;
import semantic_check.exceptions.attributes.AnchorHrefNoValueException;
import semantic_check.exceptions.attributes.AnchorNoHrefException;
import semantic_check.exceptions.attributes.ImageNoSrcException;
import semantic_check.exceptions.attributes.ImageSrcNoValueException;

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
        if (node instanceof HTMLTagNode) {
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
            AttributeNode srcAttr = null;
            if (node.getAttributes() != null) {

                for (AttributeNode attr : node.getAttributes()) {
                    if (attr.getName().equals(SRC_ATTR_NAME)) {
                        hasSrc = true;
                        srcAttr = attr;
                        if (!((HTMLAttributeNode) attr).getValue().equals("\"\"")) {
                            hasValue = true;
                        }
                        break;
                    }
                }
            }

            if (!hasSrc) {
                this.exceptions.add(new ImageNoSrcException(node.getLine(), node.getCol()));
            } else if (!hasValue) {
                this.exceptions.add(new ImageSrcNoValueException(srcAttr.getLine(), srcAttr.getCol()));
            }
        }
    }

    private void checkAnchorTag(HTMLTagNode node) {
        if (node.getName().equals(ANCHOR_TAG_NAME)) {

            //check has href attribute
            boolean hasHref = false;
            boolean hasValue = false;
            AttributeNode anchorAttr = null;
            if (node.getAttributes() != null) {

                for (AttributeNode attr : node.getAttributes()) {
                    if (attr.getName().equals(HREF_ATTR_NAME)) {
                        hasHref = true;
                        anchorAttr = attr;
                        if (!((HTMLAttributeNode) attr).getValue().equals("\"\"")) {
                            hasValue = true;
                        }
                        break;
                    }
                }
            }

            if (!hasHref) {
                this.exceptions.add(new AnchorNoHrefException(node.getLine(), node.getCol()));
            } else if (!hasValue) {
                this.exceptions.add(new AnchorHrefNoValueException(anchorAttr.getLine(), anchorAttr.getCol()));
            }
        }
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }
}
