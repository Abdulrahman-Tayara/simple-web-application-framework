package semantic_check.exceptions;

public class SemanticException extends Exception {

    private final int line;
    private final int col;

    public SemanticException(String message, int line, int col) {
        super(message);
        this.line = line;
        this.col = col;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " in line: " + line + ", col: " + col + ".\n";
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + getMessage();
    }
}
