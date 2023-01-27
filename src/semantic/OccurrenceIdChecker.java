package semantic_check;

import ast.nodes.Node;
import ast.nodes.attribute.AttributeNode;
import ast.nodes.html.HTMLTagNode;
import semantic_check.exceptions.attributes.IdIsAlreadyInUseException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OccurrenceIdChecker implements IChecker {
    private final Set<String> ids = new HashSet<>();

    private List<Exception> exceptions = new ArrayList<>();

    @Override
    public List<Exception> check(Node node) {
        exceptions.clear();
        if (node instanceof HTMLTagNode) {
            for (AttributeNode<?> attribute : ((HTMLTagNode) node).getAttributes()) {
                if (attribute.getName().equals("id")) {
                    String value = (String) attribute.getValue();
                    if (ids.contains(value)) {
                        exceptions.add(new IdIsAlreadyInUseException(attribute.getLine(), attribute.getCol()));
                    } else {
                        ids.add(value);
                    }
                }
            }
        }
        return exceptions;
    }
}
