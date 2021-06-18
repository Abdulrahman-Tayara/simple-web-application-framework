package semantic_check.exceptions.attributes;

import semantic_check.exceptions.SemanticException;

public class CpStructuralViolationException extends SemanticException {

    private static final String MESSAGE = "A tag can't have more than one structural attribute (\"cp-if, cp-if-else, cp-else, cp-show, cp-hide, cp-case, cp-for\")";

    public CpStructuralViolationException(int line, int col) {
        super(MESSAGE, line, col);
    }
}
