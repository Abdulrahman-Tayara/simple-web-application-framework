package cg;

import ast.nodes.Node;
import ast.nodes.attribute.AttributeNode;
import ast.nodes.attribute.HTMLAttributeNode;
import ast.nodes.html.HTMLElementNode;
import ast.nodes.html.HTMLTagNode;
import ast.nodes.html.HtmlDocumentNode;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class IdsGenerator {

    public static HtmlDocumentNode injectRandomIds(HtmlDocumentNode node) {
        IdsGenerator idsGenerator = new IdsGenerator();
        idsGenerator.generate(node);
        return node;
    }

    public void generate(Node node) {
        if (node instanceof HtmlDocumentNode) {
            HtmlDocumentNode htmlDocNode = ((HtmlDocumentNode) node);

            for (HTMLElementNode elementNode : htmlDocNode.getElements()) {
                generate(elementNode);
            }
        } else if (node instanceof HTMLTagNode) {
            HTMLTagNode tagNode = ((HTMLTagNode) node);

            if (!tagHasId(tagNode)) {
                tagNode.getAttributes().add(0, new HTMLAttributeNode("id", generateRandomId()));
            }

            if (tagNode.hasContent()) {
                for (HTMLElementNode elementNode : tagNode.getContent()) {
                    generate(elementNode);
                }
            }
        }
    }

    private boolean tagHasId(HTMLTagNode tagNode) {
        for (AttributeNode<?> attribute : tagNode.getAttributes()) {
            if (attribute.getName().equals("id")) {
                return true;
            }
        }
        return false;
    }

    private String generateRandomId() {
        try {
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
            int generatedId = prng.nextInt();
            if (generatedId <= 0) {
                return generateRandomId();
            }
            return Integer.valueOf(generatedId).toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException();
        }
    }
}
