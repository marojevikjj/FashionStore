package onlineshopapp.fashionstore.model.exceptions;

public class InvalidClothesIdException extends RuntimeException {

    public InvalidClothesIdException(){
        super(String.format("Please enter a valid product id!"));
    }

}
