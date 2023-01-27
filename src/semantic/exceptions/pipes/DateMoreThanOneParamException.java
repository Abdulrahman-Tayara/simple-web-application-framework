package semantic_check.exceptions.pipes;

import semantic_check.exceptions.SemanticException;

public class DateMoreThanOneParamException extends SemanticException {

    private static final String MESSAGE = "Date function of pipe cannot have more than one param";

    public DateMoreThanOneParamException(int line, int col) {
        super(MESSAGE, line, col);
    }
}
