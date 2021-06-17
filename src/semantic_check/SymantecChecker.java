package semantic_check;

import SymbolTable.BasicScope;
import SymbolTable.Symbol;
import SymbolTable.attribute.CpAttribute;
import SymbolTable.scopes.HtmlBasicScope;
import ast.nodes.Node;
import ast.nodes.html.HTMLElementNode;
import ast.nodes.html.HTMLTagNode;
import ast.nodes.html.HtmlDocumentNode;

import java.util.*;

public class SymantecChecker {

    List<Exception> totalExceptions = new ArrayList<>();


    final HtmlDocumentNode astTree;
    final BasicScope scope;

    List<IChecker> checkers = new ArrayList<>();

    public SymantecChecker(HtmlDocumentNode astTree, BasicScope scope) {
        this.astTree = astTree;
        this.scope = scope;

        checkers.add(new OccurrenceIdChecker());
        checkers.add(new AttributeChecker());
        checkers.add(new StructuralChecker());
        checkers.add(new PipesChecker());
    }

    public void execute() {
        execute(astTree);
        execute(scope);
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


    boolean existsUl_Ol = false;
    boolean existsCpApp = false;

    public void execute(BasicScope scope) {

        System.out.println("scope " + scope.getScopeName());
        for (Symbol symbol : scope.symbols) {

            if (symbol instanceof HtmlBasicScope) {
                System.out.println(((HtmlBasicScope) symbol).tagName);

                addChanges((HtmlBasicScope) symbol);

                checkThings((HtmlBasicScope) symbol);

                execute((BasicScope) symbol);

                undoChanges();
            }


        }


    }

    private void checkThings(HtmlBasicScope scope) {

        if (scope.tagName.equals("li")) {
            if (!existsUl_Ol) {
                this.totalExceptions.add(new Exception("there is no ul-ol"));
            }
        }


    }

    private void undoChanges() {

        this.existsUl_Ol = false;

    }

    private void addChanges(HtmlBasicScope scope) {

        //li
        if (scope.tagName.equals("ul") ||
                scope.tagName.equals("ol")
        ) {
            this.existsUl_Ol = true;
        }


        //cp-app
        for (Symbol symbol : scope.symbols) {
            if (symbol instanceof CpAttribute
                    && ((CpAttribute) symbol).attributeName.equals("cp-app")) {

                if (this.existsCpApp) {
                    this.totalExceptions.add(new Exception("nested cp app"));
                } else {
                    this.existsCpApp = true;
                }
            }


        }
    }
}
