package semantic_check.exceptions.pipes;

import semantic_check.exceptions.SemanticException;

public class InvalidDateFormatException extends SemanticException {

    private static final String MESSAGE = "Date function of pipe has an invalid format";

    public InvalidDateFormatException(int line, int col) {
        super(MESSAGE, line, col);
    }
}
