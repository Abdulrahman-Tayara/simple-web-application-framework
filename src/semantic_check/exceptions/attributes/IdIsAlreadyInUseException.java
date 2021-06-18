package semantic_check.exceptions.attributes;

import semantic_check.exceptions.SemanticException;

public class IdIsAlreadyInUseException extends SemanticException {

    private static final String MESSAGE = "Id value is duplicated in document";

    public IdIsAlreadyInUseException(int line, int col) {
        super(MESSAGE, line, col);
    }
}
