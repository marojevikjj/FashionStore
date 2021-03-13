package onlineshopapp.fashionstore.model.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {

    public InvalidUserCredentialsException() {
        super("The username or password does not match our records!");
    }
}

