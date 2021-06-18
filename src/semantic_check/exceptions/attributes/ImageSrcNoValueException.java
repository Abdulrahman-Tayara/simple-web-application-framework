package semantic_check.exceptions.attributes;

import semantic_check.exceptions.SemanticException;

public class ImageSrcNoValueException extends SemanticException {
    private static final String MESSAGE = "Image src attribute has no value";
    public ImageSrcNoValueException(int line, int col) {
        super(MESSAGE, line, col);
    }
}
