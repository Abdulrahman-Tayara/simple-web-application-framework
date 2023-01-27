package semantic_check.exceptions.pipes;

import semantic_check.exceptions.SemanticException;

public class LowerFuncHasParamsException extends SemanticException {

    private static final String MESSAGE = "Lower function of pipe should not have any params";

    public LowerFuncHasParamsException(int line, int col) {
        super(MESSAGE, line, col);
    }
}
