package ro.alexprojects.demo.mappers;

import ro.alexprojects.demo.dto.ingredient.IngredientRequest;
import ro.alexprojects.demo.dto.ingredient.IngredientResponse;
import ro.alexprojects.demo.dto.meal.MealRequest;
import ro.alexprojects.demo.dto.meal.MealResponse;
import ro.alexprojects.demo.dto.order.OrderRequest;
import ro.alexprojects.demo.dto.order.OrderResponse;
import ro.alexprojects.demo.model.ingredient.Ingredient;
import ro.alexprojects.demo.model.meal.Meal;
import ro.alexprojects.demo.model.order.Order;

import java.util.List;
import java.util.Set;

public interface MapStructMapper {
    IngredientRequest ingredientToIngredientRequest(Ingredient ingredient);
    IngredientResponse ingredientToIngredientResponse(Ingredient ingredient);
    Ingredient ingredientRequestToIngredient(IngredientRequest ingredientRequest);
    Set<IngredientRequest> ingredientsToIngredientRequests(Set<Ingredient> ingredients);
    Set<IngredientResponse> ingredientsToIngredientResponses(Set<Ingredient> ingredients);
    Set<Ingredient> ingredientRequestsToIngredients(Set<IngredientRequest> ingredientRequests);
    Set<Ingredient> ingredientResponsesToIngredients(Set<IngredientResponse> ingredientResponses);

    MealRequest mealToMealRequest(Meal meal);
    MealResponse mealToMealResponse(Meal meal);
    Meal mealRequestToMeal(MealRequest mealRequest);
    List<MealRequest> mealsToMealRequests(List<Meal> meals);
    List<MealResponse> mealsToMealResponses(List<Meal> meals);
    List<Meal> mealRequestsToMeals(List<MealRequest> mealRequests);
    List<Meal> mealResponsesToMeals(List<MealResponse> mealResponses);

    OrderRequest orderToOrderRequest(Order order);
    OrderResponse orderToOrderResponse(Order order);
    Order orderRequestToOrder(OrderRequest order);
    List<OrderResponse> ordersToOrderResponses(List<Order> orders);
}
