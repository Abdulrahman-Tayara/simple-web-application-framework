package semantic_check.exceptions;

public class IdIsAlreadyInUseException extends SemanticException {
    public IdIsAlreadyInUseException() {
        super("Id is already in use");
    }
}
