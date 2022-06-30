package ro.alexprojects.demo.util;

import ro.alexprojects.demo.dto.meal.MealRequest;
import ro.alexprojects.demo.exceptions.InvalidMealPriceException;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;

public interface Calculator {
    static BigDecimal calculateTotalPrice(List<MealRequest> meals) {

        return meals.stream()
                .filter(mealRequest -> {
                    if(!isPositive(mealRequest.getPrice().compareTo(ZERO))) {
                        throw new InvalidMealPriceException(mealRequest.getPrice());
                    }

                    return true;
                })
                .map(MealRequest::getPrice)
                .reduce(ZERO, BigDecimal::add);
    }

    static boolean isPositive(int compareToResult) {
        return compareToResult == 1;
    }
}
