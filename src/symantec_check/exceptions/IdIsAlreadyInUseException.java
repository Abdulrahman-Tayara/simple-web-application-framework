package symantec_check.exceptions;

public class IdIsAlreadyInUseException extends SymantecException{
    public IdIsAlreadyInUseException() {
        super("Id is already in use");
    }
}
