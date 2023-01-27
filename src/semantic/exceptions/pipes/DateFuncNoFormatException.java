package semantic_check.exceptions.pipes;

import semantic_check.exceptions.SemanticException;

public class DateFuncNoFormatException extends SemanticException {

    private static final String MESSAGE = "Date function of pipe is not provided with a format";
    public DateFuncNoFormatException(int line, int col) {
        super(MESSAGE, line, col);
    }
}
