package semantic_check.exceptions.attributes;

import semantic_check.exceptions.SemanticException;

public class AnchorHrefNoValueException extends SemanticException {
    private static final String MESSAGE = "Anchor href attribute has no value";
    public AnchorHrefNoValueException(int line, int col) {
        super(MESSAGE, line, col);
    }
}
