package symantec_check;

import ast.nodes.attribute.AttributeNode;
import ast.nodes.attribute.HTMLAttributeNode;
import ast.nodes.html.HTMLElementNode;
import ast.nodes.html.HTMLTagNode;
import ast.nodes.html.HtmlDocumentNode;
import symantec_check.exceptions.IdIsAlreadyInUseException;

import java.util.*;

public class SymantecChecker {

    List<Exception> totalExceptions = new ArrayList<>();

    final HtmlDocumentNode astTree;

    AttributeChecker attributeChecker = new AttributeChecker();
    PipesChecker pipesChecker = new PipesChecker();
    StructuralChecker structuralChecker = new StructuralChecker();

    public SymantecChecker(HtmlDocumentNode astTree) {
        this.astTree = astTree;
    }

    public void execute() throws Exception {
//        OccuranceIdChecker.check(astTree);

        attributeChecker.check(astTree);
        totalExceptions.addAll(attributeChecker.getExceptions());

        pipesChecker.check(astTree);
        totalExceptions.addAll(pipesChecker.getExceptions());

        structuralChecker.check(astTree);
        totalExceptions.addAll(structuralChecker.getExceptions());
    }

    public List<Exception> getTotalExceptions() {
        return totalExceptions;
    }
}
