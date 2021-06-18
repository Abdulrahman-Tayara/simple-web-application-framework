package semantic_check.exceptions.pipes;

import semantic_check.exceptions.SemanticException;

public class CurrencyFuncNotOneParamException extends SemanticException {

    private static final String MESSAGE = "Currency function in pipe should have one parameter";

    public CurrencyFuncNotOneParamException(int line, int col) {
        super(MESSAGE, line, col);
    }
}
