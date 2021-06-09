package semantic_check;

import ast.nodes.Node;
import ast.nodes.html.HTMLElementNode;
import ast.nodes.html.HTMLTagNode;
import ast.nodes.html.HtmlDocumentNode;

import java.util.*;

public class SymantecChecker {

    List<Exception> totalExceptions = new ArrayList<>();

    final HtmlDocumentNode astTree;

    List<IChecker> checkers = new ArrayList<>();

    public SymantecChecker(HtmlDocumentNode astTree) {
        this.astTree = astTree;

        checkers.add(new OccurrenceIdChecker());
        checkers.add(new AttributeChecker());
        checkers.add(new StructuralChecker());
        checkers.add(new PipesChecker());
    }

    public void execute() {
        execute(astTree);
    }

    public void execute(Node node) {
        if (node instanceof HtmlDocumentNode) {
            HtmlDocumentNode htmlDocNode = ((HtmlDocumentNode) node);
            for (HTMLElementNode elementNode : htmlDocNode.getElements()) {
                execute(elementNode);
            }
        } else if (node instanceof HTMLTagNode) {
            check(node);
            HTMLTagNode tagNode = ((HTMLTagNode) node);

            if (tagNode.hasContent()) {
                for (HTMLElementNode elementNode : tagNode.getContent()) {
                    execute(elementNode);
                }
            }

        } else {
            check(node);
        }
    }

    // Pass the node to the checkers
    private void check(Node node) {
        checkers.forEach(c -> totalExceptions.addAll(c.check(node)));
    }

    public List<Exception> getTotalExceptions() {
        return totalExceptions;
    }
}
