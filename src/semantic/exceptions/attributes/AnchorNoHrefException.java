package semantic_check.exceptions.attributes;

import semantic_check.exceptions.SemanticException;

public class AnchorNoHrefException extends SemanticException {

    private static final String MESSAGE = "Anchor tag does not have an href attribute";

    public AnchorNoHrefException(int line, int col) {
        super(MESSAGE, line, col);
    }
}
