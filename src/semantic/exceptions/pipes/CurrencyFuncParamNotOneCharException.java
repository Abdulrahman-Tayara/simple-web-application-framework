package semantic_check.exceptions.pipes;

import semantic_check.exceptions.SemanticException;

public class CurrencyFuncParamNotOneCharException extends SemanticException {

    private static final String MESSAGE = "Currency function parameter should be exactly one character only";

    public CurrencyFuncParamNotOneCharException(int line, int col) {
        super(MESSAGE, line, col);
    }
}
