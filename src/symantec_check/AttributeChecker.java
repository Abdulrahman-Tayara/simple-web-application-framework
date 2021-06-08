package symantec_check;

import ast.nodes.attribute.AttributeNode;
import ast.nodes.attribute.HTMLAttributeNode;
import ast.nodes.html.HTMLElementNode;
import ast.nodes.html.HTMLTagNode;
import ast.nodes.html.HtmlDocumentNode;

import java.util.ArrayList;
import java.util.List;

public class AttributeChecker {

    List<Exception> exceptions = new ArrayList<>();

    private static final String IMAGE_TAG_NAME = "img";
    private static final String ANCHOR_TAG_NAME = "a";

    private static final String SRC_ATTR_NAME = "src";
    private static final String HREF_ATTR_NAME = "href";

    public void check(HtmlDocumentNode tree) {
        for (HTMLElementNode element : tree.getElements()) {
            if (element instanceof HTMLTagNode) {
                checkImageTag((HTMLTagNode) element);
                checkAnchorTag(((HTMLTagNode) element));
                checkElementTag(((HTMLTagNode) element));
            }
        }
    }

    private void checkElementTag(HTMLTagNode tagNode) {
        checkImageTag(tagNode);
        checkAnchorTag(tagNode);
        System.out.println(tagNode.getName());

        if (tagNode.getContent() != null) {
            for (HTMLElementNode elementNode : tagNode.getContent()) {
                if (elementNode instanceof HTMLTagNode) {
                    checkElementTag(((HTMLTagNode) elementNode));
                }
            }
        }

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
            }
            else if (!hasValue) {
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
            }
            else if (!hasValue) {
                this.exceptions.add(new Exception("href does not have a value"));
            }
        }
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }
}
