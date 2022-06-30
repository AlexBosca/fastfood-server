package ro.alexprojects.demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String orderID) {
        super(String.format("No order found with ID: %s", orderID));
    }
}
