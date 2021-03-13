package onlineshopapp.fashionstore.model.exceptions;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException() {
        super(String.format("Please enter a valid email address!"));
    }
}
