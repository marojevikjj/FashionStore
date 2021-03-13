package onlineshopapp.fashionstore.model.exceptions;

public class InvalidUsernameOrPasswordException extends RuntimeException {
    public InvalidUsernameOrPasswordException() {
        super("Please enter valid values for all fields!");
    }
}
