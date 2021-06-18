package semantic_check.exceptions.pipes;

import semantic_check.exceptions.SemanticException;

public class UpperFuncHasParamsException extends SemanticException {

    private static final String MESSAGE = "Upper function of pipe should not have any params";

    public UpperFuncHasParamsException(int line, int col) {
        super(MESSAGE, line, col);
    }
}