package semantic_check.exceptions.attributes;

import semantic_check.exceptions.SemanticException;

public class ImageNoSrcException extends SemanticException {
    private static final String MESSAGE = "Image tag does not have a src attribute";
    public ImageNoSrcException(int line, int col) {
        super(MESSAGE, line, col);
    }
}
