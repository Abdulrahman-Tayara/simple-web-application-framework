package symantec_check;

import ast.nodes.attribute.AttributeNode;
import ast.nodes.html.HTMLElementNode;
import ast.nodes.html.HTMLTagNode;
import ast.nodes.html.HtmlDocumentNode;
import symantec_check.exceptions.IdIsAlreadyInUseException;

import java.util.HashSet;
import java.util.Set;

public class OccuranceIdChecker {
    static Set<String> ids = new HashSet<>();

    public static void check(HtmlDocumentNode tree) throws Exception {

        for (HTMLElementNode element : tree.getElements()) {
            if (element instanceof HTMLTagNode) {
                examineHtmlTagIds((HTMLTagNode) element);
            }
        }

    }

    private static void examineHtmlTagIds(HTMLTagNode tagNode) throws Exception {
        for (AttributeNode attribute : tagNode.getAttributes()) {
            if (attribute.getName().equals("id")) {
                String value = (String) attribute.getValue();
                if (ids.contains(value)) {
                    throw new IdIsAlreadyInUseException();
                } else {
                    ids.add(value);
                    System.out.println(value);
                }
            }
        }

        if (tagNode.getContent() != null) {

            for (HTMLElementNode elementNode : tagNode.getContent()) {
                if (elementNode instanceof HTMLTagNode) {

                    System.out.println((((HTMLTagNode) elementNode)).getName());
                    examineHtmlTagIds((HTMLTagNode) elementNode);
                }
            }
        }
    }
}
