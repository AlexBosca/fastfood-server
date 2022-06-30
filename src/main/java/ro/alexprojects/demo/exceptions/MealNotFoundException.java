package ro.alexprojects.demo.exceptions;

public class MealNotFoundException extends RuntimeException {

    public MealNotFoundException(String orderID) {
        super(String.format("No order found with ID: %s", orderID));
    }
}
