package semantic_check;

import ast.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class Checker implements IChecker {

    protected List<Exception> exceptions = new ArrayList<>();

    @Override
    public List<Exception> check(Node node) {
        exceptions.clear();

        checkNode(node);

        return exceptions;
    }

    public void checkNode(Node node) {

    }
}
