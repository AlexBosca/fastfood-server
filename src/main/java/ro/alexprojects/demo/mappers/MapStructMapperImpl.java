package ro.alexprojects.demo.mappers;

import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;
import ro.alexprojects.demo.dto.ingredient.IngredientRequest;
import ro.alexprojects.demo.dto.ingredient.IngredientResponse;
import ro.alexprojects.demo.dto.meal.MealRequest;
import ro.alexprojects.demo.dto.meal.MealResponse;
import ro.alexprojects.demo.dto.order.OrderRequest;
import ro.alexprojects.demo.dto.order.OrderResponse;
import ro.alexprojects.demo.model.ingredient.Ingredient;
import ro.alexprojects.demo.model.meal.Meal;
import ro.alexprojects.demo.model.order.Order;
import ro.alexprojects.demo.util.Calculator;

import javax.annotation.processing.Generated;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ro.alexprojects.demo.model.order.OrderStatus.SUBMITTED;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2021-03-11T19:21:44+0100",
        comments = "version: 1.4.2.Final, compiler: javac, environment: Java 13.0.2 (Oracle Corporation)"
)
@Component
public class MapStructMapperImpl implements MapStructMapper {
    @Override
    public IngredientRequest ingredientToIngredientRequest(@NotNull Ingredient ingredient) {
        IngredientRequest ingredientRequest = IngredientRequest.builder()
                .name(ingredient.getName())
                .allergen(ingredient.isAllergen())
                .build();

        return ingredientRequest;
    }

    @Override
    public IngredientResponse ingredientToIngredientResponse(@NotNull Ingredient ingredient) {
        IngredientResponse ingredientResponse = IngredientResponse.builder()
                .name(ingredient.getName())
                .allergen(ingredient.isAllergen())
                .build();

        return ingredientResponse;
    }

    @Override
    public Ingredient ingredientRequestToIngredient(@NotNull IngredientRequest ingredientRequest) {
        Ingredient ingredient = Ingredient.builder()
                .name(ingredientRequest.getName())
                .allergen(ingredientRequest.isAllergen())
                .build();

        return ingredient;
    }

    @Override
    public Set<IngredientRequest> ingredientsToIngredientRequests(@NotNull Set<Ingredient> ingredients) {
        Set<IngredientRequest> ingredientRequests = ingredients.stream()
                .map(ingredient -> IngredientRequest.builder()
                        .name(ingredient.getName())
                        .allergen(ingredient.isAllergen())
                        .build())
                .collect(Collectors.toSet());

        return ingredientRequests;
    }

    @Override
    public Set<IngredientResponse> ingredientsToIngredientResponses(@NotNull Set<Ingredient> ingredients) {
        Set<IngredientResponse> ingredientResponses = ingredients.stream()
                .map(ingredient -> IngredientResponse.builder()
                        .name(ingredient.getName())
                        .allergen(ingredient.isAllergen())
                        .build())
                .collect(Collectors.toSet());

        return ingredientResponses;
    }

    @Override
    public Set<Ingredient> ingredientRequestsToIngredients(Set<IngredientRequest> ingredientRequests) {
        Set<Ingredient> ingredients = ingredientRequests.stream()
                .map(ingredientRequest -> Ingredient.builder()
                        .name(ingredientRequest.getName())
                        .allergen(ingredientRequest.isAllergen())
                        .build())
                .collect(Collectors.toSet());

        return ingredients;
    }

    @Override
    public Set<Ingredient> ingredientResponsesToIngredients(Set<IngredientResponse> ingredientResponses) {
        Set<Ingredient> ingredients = ingredientResponses.stream()
                .map(ingredientResponse -> Ingredient.builder()
                        .name(ingredientResponse.getName())
                        .allergen(ingredientResponse.isAllergen())
                        .build())
                .collect(Collectors.toSet());

        return ingredients;
    }

    @Override
    public MealRequest mealToMealRequest(@NotNull Meal meal) {
        MealRequest mealRequest = MealRequest.builder()
                .name(meal.getName())
                .status(meal.getStatus())
                .price(meal.getPrice())
                .ingredients(ingredientsToIngredientRequests(meal.getIngredients()))
                .imageUrl(meal.getImageUrl())
                .build();

        return mealRequest;
    }

    @Override
    public MealResponse mealToMealResponse(@NotNull Meal meal) {
        MealResponse mealResponse = MealResponse.builder()
                .name(meal.getName())
                .status(meal.getStatus())
                .price(meal.getPrice())
                .ingredients(ingredientsToIngredientResponses(meal.getIngredients()))
                .imageUrl(meal.getImageUrl())
                .build();

        return mealResponse;
    }

    @Override
    public Meal mealRequestToMeal(@NotNull MealRequest mealRequest) {
        Meal meal = Meal.builder()
                .name(mealRequest.getName())
                .status(mealRequest.getStatus())
                .price(mealRequest.getPrice())
                .ingredients(ingredientRequestsToIngredients(mealRequest.getIngredients()))
                .imageUrl(mealRequest.getImageUrl())
                .build();

        return meal;
    }

    @Override
    public List<MealRequest> mealsToMealRequests(@NotNull List<Meal> meals) {
        List<MealRequest> mealRequests = meals.stream()
                .map(meal -> MealRequest.builder()
                        .name(meal.getName())
                        .status(meal.getStatus())
                        .price(meal.getPrice())
                        .ingredients(ingredientsToIngredientRequests(meal.getIngredients()))
                        .imageUrl(meal.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        return mealRequests;
    }

    @Override
    public List<MealResponse> mealsToMealResponses(@NotNull List<Meal> meals) {
        List<MealResponse> mealResponses = meals.stream()
                .map(meal -> MealResponse.builder()
                        .name(meal.getName())
                        .status(meal.getStatus())
                        .price(meal.getPrice())
                        .ingredients(ingredientsToIngredientResponses(meal.getIngredients()))
                        .imageUrl(meal.getImageUrl())
                        .build()
                )
                .collect(Collectors.toList());

        return mealResponses;
    }

    @Override
    public List<Meal> mealRequestsToMeals(List<MealRequest> mealRequests) {
        List<Meal> meals = mealRequests.stream()
                .map(mealRequest -> Meal.builder()
                        .name(mealRequest.getName())
                        .status(mealRequest.getStatus())
                        .price(mealRequest.getPrice())
                        .ingredients(ingredientRequestsToIngredients(mealRequest.getIngredients()))
                        .imageUrl(mealRequest.getImageUrl())
                        .build()
                )
                .collect(Collectors.toList());

        return meals;
    }

    @Override
    public List<Meal> mealResponsesToMeals(List<MealResponse> mealResponses) {
        List<Meal> meals = mealResponses.stream()
                .map(mealResponse -> Meal.builder()
                        .name(mealResponse.getName())
                        .status(mealResponse.getStatus())
                        .price(mealResponse.getPrice())
                        .ingredients(ingredientResponsesToIngredients(mealResponse.getIngredients()))
                        .imageUrl(mealResponse.getImageUrl())
                        .build()
                )
                .collect(Collectors.toList());

        return meals;
    }

    @Override
    public OrderRequest orderToOrderRequest(@NotNull Order order) {
        OrderRequest orderRequest = OrderRequest.builder()
                .orderID(order.getOrderID())
                .meals(mealsToMealRequests(order.getMeals()))
                .build();

        return orderRequest;
    }

    @Override
    public OrderResponse orderToOrderResponse(@NotNull Order order) {
        OrderResponse orderResponse = OrderResponse.builder()
                .orderID(order.getOrderID())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .meals(mealsToMealResponses(order.getMeals()))
                .build();

        return orderResponse;
    }

    @Override
    public Order orderRequestToOrder(@NotNull OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderID(orderRequest.getOrderID())
                .meals(mealRequestsToMeals(orderRequest.getMeals()))
                .status(SUBMITTED)
                .totalPrice(Calculator.calculateTotalPrice(orderRequest.getMeals()))
                .build();

        return order;
    }

    @Override
    public List<OrderResponse> ordersToOrderResponses(List<Order> orders) {
        List<OrderResponse> orderReponses = orders.stream()
                .map(order -> OrderResponse.builder()
                        .orderID(order.getOrderID())
                        .status(order.getStatus())
                        .totalPrice(order.getTotalPrice())
                        .meals(mealsToMealResponses(order.getMeals()))
                        .build()
                )
                .collect(Collectors.toList());

        return orderReponses;
    }
}
