package semantic_check;

import ast.nodes.Node;

import java.util.List;

public interface IChecker {

    List<Exception> check(Node node);

}