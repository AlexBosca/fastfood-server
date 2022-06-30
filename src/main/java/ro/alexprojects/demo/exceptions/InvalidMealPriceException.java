package ro.alexprojects.demo.exceptions;

import java.math.BigDecimal;

public class InvalidMealPriceException extends RuntimeException {
    public InvalidMealPriceException(BigDecimal mealPrice) {
        super(String.format("Invalid meal price: %d", mealPrice));
    }
}
