package onlineshopapp.fashionstore.model.exceptions;

public class InvalidArgumentsException extends RuntimeException {

    public InvalidArgumentsException() {
        super("Incorrect username or password");
    }
}
