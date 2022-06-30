package ro.alexprojects.demo.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import ro.alexprojects.demo.dto.ingredient.IngredientRequest;
import ro.alexprojects.demo.dto.ingredient.IngredientResponse;
import ro.alexprojects.demo.dto.meal.MealRequest;
import ro.alexprojects.demo.dto.meal.MealResponse;
import ro.alexprojects.demo.dto.order.OrderRequest;
import ro.alexprojects.demo.dto.order.OrderResponse;
import ro.alexprojects.demo.model.ingredient.Ingredient;
import ro.alexprojects.demo.model.meal.Meal;
import ro.alexprojects.demo.model.order.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static ro.alexprojects.demo.model.meal.MealStatus.AVAILABLE;
import static ro.alexprojects.demo.model.order.OrderStatus.SUBMITTED;

class MapStructMapperImplTest {

    private MapStructMapper mapStructMapper;

    @BeforeEach
    void setUp() {
        mapStructMapper = new MapStructMapperImpl();
    }

    @Test
    @DisplayName("Should map ingredient to ingredient request")
    void shouldMapIngredientToIngredientRequest() {
        Ingredient ingredient = new Ingredient(123L, "Eggs", true);
        IngredientRequest ingredientRequest = mapStructMapper.ingredientToIngredientRequest(ingredient);

        assertThat(ingredient.getName()).isEqualTo(ingredientRequest.getName());
        assertThat(ingredient.isAllergen()).isEqualTo(ingredientRequest.isAllergen());
    }

    @Test
    @DisplayName("Should map ingredient to ingredient response")
    void shouldMapIngredientToIngredientResponse() {
        Ingredient ingredient = new Ingredient(123L, "Eggs", true);
        IngredientResponse ingredientResponse = mapStructMapper.ingredientToIngredientResponse(ingredient);

        assertThat(ingredient.getName()).isEqualTo(ingredientResponse.getName());
        assertThat(ingredient.isAllergen()).isEqualTo(ingredientResponse.isAllergen());
    }

    @Test
    @DisplayName("Should map ingredient request to ingredient")
    void shouldMapIngredientRequestToIngredient() {
        IngredientRequest ingredientRequest = IngredientRequest.builder()
                .name("Eggs")
                .allergen(true)
                .build();

        Ingredient ingredient = mapStructMapper.ingredientRequestToIngredient(ingredientRequest);

        assertThat(ingredientRequest.getName()).isEqualTo(ingredient.getName());
        assertThat(ingredientRequest.isAllergen()).isEqualTo(ingredient.isAllergen());
    }

    @Test
    @DisplayName("Should map ingredients to ingredient requests")
    void shouldMapIngredientsToIngredientRequests() {
        Set<Ingredient> ingredients = Set.of(
                new Ingredient(123L, "Eggs", true),
                new Ingredient(124L, "Tomato", false),
                new Ingredient(125L, "Garlic", false)
        );

        Set<IngredientRequest> ingredientRequests = mapStructMapper.ingredientsToIngredientRequests(ingredients);

        assertThat(ingredientRequests)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(ingredients);
    }

    @Test
    @DisplayName("Should map ingredients to ingredient responses")
    void shouldMapIngredientsToIngredientResponses() {
        Set<Ingredient> ingredients = Set.of(
                new Ingredient(123L, "Eggs", true),
                new Ingredient(124L, "Tomato", false),
                new Ingredient(125L, "Garlic", false)
        );

        Set<IngredientResponse> ingredientResponses = mapStructMapper.ingredientsToIngredientResponses(ingredients);

        assertThat(ingredientResponses)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(ingredients);
    }

    @Test
    @DisplayName("Should map ingredient requests to ingredients")
    void shouldMapIngredientRequestsToIngredients() {
        Set<IngredientRequest> ingredientRequests = Set.of(
                IngredientRequest.builder().name("Eggs").allergen(true).build(),
                IngredientRequest.builder().name("Tomato").allergen(false).build(),
                IngredientRequest.builder().name("Garlic").allergen(true).build()
        );

        Set<Ingredient> ingredients = mapStructMapper.ingredientRequestsToIngredients(ingredientRequests);

        assertThat(ingredients)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(ingredientRequests);
    }

    @Test
    @DisplayName("Should map ingredient responses to ingredients")
    void shouldMapIngredientResponsesToIngredients() {
        Set<IngredientResponse> ingredientResponses = Set.of(
                IngredientResponse.builder().name("Eggs").allergen(true).build(),
                IngredientResponse.builder().name("Tomato").allergen(false).build(),
                IngredientResponse.builder().name("Garlic").allergen(true).build()
        );

        Set<Ingredient> ingredients = mapStructMapper.ingredientResponsesToIngredients(ingredientResponses);

        assertThat(ingredients)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(ingredientResponses);
    }

    @Test
    @DisplayName("Should map meal to meal request")
    void shouldMapMealToMealRequest() {
        Meal meal = new Meal(123L,
                "Pizza Margherita",
                AVAILABLE,
                BigDecimal.valueOf(35.00),
                Set.of(
                        new Ingredient(123L, "Eggs", true),
                        new Ingredient(124L, "Tomato", false),
                        new Ingredient(125L, "Garlic", false)
                ),
                "../pizza/margherita"
        );

        MealRequest mealRequest = mapStructMapper.mealToMealRequest(meal);

        assertThat(mealRequest)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(meal);
    }

    @Test
    @DisplayName("Should map meal to meal response")
    void shouldMapMealToMealResponse() {
        Meal meal = new Meal(123L,
                "Pizza Margherita",
                AVAILABLE,
                BigDecimal.valueOf(35.00),
                Set.of(
                        new Ingredient(123L, "Eggs", true),
                        new Ingredient(124L, "Tomato", false),
                        new Ingredient(125L, "Garlic", false)
                ),
                "../pizza/margherita"
        );

        MealResponse mealResponse = mapStructMapper.mealToMealResponse(meal);

        assertThat(mealResponse)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(meal);
    }

    @Test
    @DisplayName("Should map meal request to meal")
    void shouldMapMealRequestToMeal() {
        MealRequest mealRequest = MealRequest.builder()
                .name("Pizza Margherita")
                .status(AVAILABLE)
                .price(BigDecimal.valueOf(35.00))
                .ingredients(Set.of(
                        IngredientRequest.builder().name("Eggs").allergen(true).build(),
                        IngredientRequest.builder().name("Tomato").allergen(false).build(),
                        IngredientRequest.builder().name("Garlic").allergen(true).build()
                ))
                .build();

        Meal meal = mapStructMapper.mealRequestToMeal(mealRequest);

        assertThat(meal)
                .usingRecursiveComparison()
                .ignoringFields("id", "ingredients")
                .isEqualTo(mealRequest);

        assertThat(meal.getIngredients())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(mealRequest.getIngredients());
    }

    @Test
    @DisplayName("Should map meals to meal requests")
    void shouldMapMealsToMealRequests() {
        List<Meal> meals = List.of(
                Meal.builder()
                        .name("Pizza Margherita")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(35.00))
                        .ingredients(Set.of(
                                new Ingredient(123L, "Eggs", true),
                                new Ingredient(124L, "Tomato", false),
                                new Ingredient(125L, "Garlic", false)
                        ))
                        .imageUrl("../pizza/margherita")
                        .build(),

                Meal.builder()
                        .name("Pulled Pork")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(32.00))
                        .ingredients(Set.of(
                                new Ingredient(123L, "Pork", false),
                                new Ingredient(124L, "Butter", true)
                        ))
                        .imageUrl("../meat/pulled-pork")
                        .build()
        );

        List<MealRequest> mealRequests = mapStructMapper.mealsToMealRequests(meals);

        assertThat(mealRequests)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(meals);
    }

    @Test
    @DisplayName("Should map meals to meal responses")
    void shouldMapMealsToMealResponses() {
        List<Meal> meals = List.of(
                Meal.builder()
                        .name("Pizza Margherita")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(35.00))
                        .ingredients(Set.of(
                                new Ingredient(123L, "Eggs", true),
                                new Ingredient(124L, "Tomato", false),
                                new Ingredient(125L, "Garlic", false)
                        ))
                        .imageUrl("../pizza/margherita")
                        .build(),

                Meal.builder()
                        .name("Pulled Pork")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(32.00))
                        .ingredients(Set.of(
                                new Ingredient(123L, "Pork", false),
                                new Ingredient(124L, "Butter", true)
                        ))
                        .imageUrl("../meat/pulled-pork")
                        .build()
        );

        List<MealResponse> mealResponses = mapStructMapper.mealsToMealResponses(meals);

        assertThat(mealResponses)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(meals);
    }

    @Test
    @DisplayName("Should map meal requests to meals")
    void shouldMapMealRequestsToMeals() {
        List<MealRequest> mealRequests = List.of(
                MealRequest.builder()
                        .name("Pizza Margherita")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(35.00))
                        .ingredients(Set.of(
                                IngredientRequest.builder().name("Eggs").allergen(true).build(),
                                IngredientRequest.builder().name("Tomato").allergen(false).build(),
                                IngredientRequest.builder().name("Garlic").allergen(true).build()
                        ))
                        .imageUrl("../pizza/margherita")
                        .build(),

                MealRequest.builder()
                        .name("Pulled Pork")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(32.00))
                        .ingredients(Set.of(
                                IngredientRequest.builder().name("Pork").allergen(false).build(),
                                IngredientRequest.builder().name("Butter").allergen(true).build()
                        ))
                        .imageUrl("../meat/pulled-pork")
                        .build()
        );

        List<Meal> meals = mapStructMapper.mealRequestsToMeals(mealRequests);

        assertThat(meals)
                .usingRecursiveComparison()
                .ignoringFields("id", "ingredients")
                .isEqualTo(mealRequests);

        assertThat(meals.get(0).getIngredients())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(mealRequests.get(0).getIngredients());

        assertThat(meals.get(1).getIngredients())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(mealRequests.get(1).getIngredients());
    }

    @Test
    @DisplayName("Should map meal responses to meals")
    void shouldMapMealResponsesToMeals() {
        List<MealResponse> mealResponses = List.of(
                MealResponse.builder()
                        .name("Pizza Margherita")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(35.00))
                        .ingredients(Set.of(
                                IngredientResponse.builder().name("Eggs").allergen(true).build(),
                                IngredientResponse.builder().name("Tomato").allergen(false).build(),
                                IngredientResponse.builder().name("Garlic").allergen(true).build()
                        ))
                        .imageUrl("../pizza/margherita")
                        .build(),

                MealResponse.builder()
                        .name("Pulled Pork")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(32.00))
                        .ingredients(Set.of(
                                IngredientResponse.builder().name("Pork").allergen(false).build(),
                                IngredientResponse.builder().name("Butter").allergen(true).build()
                        ))
                        .imageUrl("../meat/pulled-pork")
                        .build()
        );

        List<Meal> meals = mapStructMapper.mealResponsesToMeals(mealResponses);

        assertThat(meals)
                .usingRecursiveComparison()
                .ignoringFields("id", "ingredients")
                .isEqualTo(mealResponses);

        assertThat(meals.get(0).getIngredients())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(mealResponses.get(0).getIngredients());

        assertThat(meals.get(1).getIngredients())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(mealResponses.get(1).getIngredients());
    }

    @Test
    @DisplayName("Should map order to order request")
    void shouldMapOrderToOrderRequest() {
        Order order = new Order(123L, "abcd-efgh", SUBMITTED, BigDecimal.valueOf(92.00), List.of(
                Meal.builder()
                        .name("Pizza Margherita")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(35.00))
                        .ingredients(Set.of(
                                new Ingredient(123L, "Eggs", true),
                                new Ingredient(124L, "Tomato", false),
                                new Ingredient(125L, "Garlic", false)
                        ))
                        .imageUrl("../pizza/margherita")
                        .build(),

                Meal.builder()
                        .name("Pulled Pork")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(32.00))
                        .ingredients(Set.of(
                                new Ingredient(123L, "Pork", false),
                                new Ingredient(124L, "Butter", true)
                        ))
                        .imageUrl("../meat/pulled-pork")
                        .build()
        ));

        OrderRequest orderRequest = mapStructMapper.orderToOrderRequest(order);

        assertThat(orderRequest)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(order);
    }

    @Test
    @DisplayName("Should map order to order response")
    void shouldMapOrderToOrderResponse() {
        Order order = new Order(123L, "abcd-efgh", SUBMITTED, BigDecimal.valueOf(92.00), List.of(
                Meal.builder()
                        .name("Pizza Margherita")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(35.00))
                        .ingredients(Set.of(
                                new Ingredient(123L, "Eggs", true),
                                new Ingredient(124L, "Tomato", false),
                                new Ingredient(125L, "Garlic", false)
                        ))
                        .imageUrl("../pizza/margherita")
                        .build(),

                Meal.builder()
                        .name("Pulled Pork")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(32.00))
                        .ingredients(Set.of(
                                new Ingredient(123L, "Pork", false),
                                new Ingredient(124L, "Butter", true)
                        ))
                        .imageUrl("../meat/pulled-pork")
                        .build()
        ));

        OrderResponse orderResponse = mapStructMapper.orderToOrderResponse(order);

        assertThat(orderResponse)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(order);
    }

    @Test
    @DisplayName("Should map order request to order")
    void shouldMapOrderRequestToOrder() {
        OrderRequest orderRequest = OrderRequest.builder()
                .orderID("abcd-efgh")
                .meals(List.of(
                        MealRequest.builder()
                                .name("Pizza Margherita")
                                .status(AVAILABLE)
                                .price(BigDecimal.valueOf(35.00))
                                .ingredients(Set.of(
                                        IngredientRequest.builder().name("Eggs").allergen(true).build(),
                                        IngredientRequest.builder().name("Tomato").allergen(false).build(),
                                        IngredientRequest.builder().name("Garlic").allergen(true).build()
                                ))
                                .imageUrl("../pizza/margherita")
                                .build(),
                        MealRequest.builder()
                                .name("Pulled Pork")
                                .status(AVAILABLE)
                                .price(BigDecimal.valueOf(32.00))
                                .ingredients(Set.of(
                                        IngredientRequest.builder().name("Pork").allergen(false).build(),
                                        IngredientRequest.builder().name("Butter").allergen(true).build()
                                ))
                                .imageUrl("../meat/pulled-pork")
                                .build()
                ))
                .build();

        Order order = mapStructMapper.orderRequestToOrder(orderRequest);

        assertThat(order.getOrderID())
                .isEqualTo(orderRequest.getOrderID());

        assertThat(order.getMeals())
                .usingRecursiveComparison()
                .ignoringFields("id", "ingredients")
                .isEqualTo(orderRequest.getMeals());

        assertThat(order.getMeals().get(0).getIngredients())
                .usingRecursiveComparison()
                .ignoringFields("id", "ingredients")
                .isEqualTo(orderRequest.getMeals().get(0).getIngredients());

        assertThat(order.getMeals().get(1).getIngredients())
                .usingRecursiveComparison()
                .ignoringFields("id", "ingredients")
                .isEqualTo(orderRequest.getMeals().get(1).getIngredients());
    }

    @Test
    @DisplayName("Should map orders to order responses")
    void shouldMapOrdersToOrderResponses() {
        List<Order> orders = List.of(
                new Order(123L, "abcd-efgh", SUBMITTED, BigDecimal.valueOf(92.00), List.of(
                        Meal.builder()
                                .name("Pizza Margherita")
                                .status(AVAILABLE)
                                .price(BigDecimal.valueOf(35.00))
                                .ingredients(Set.of(
                                        new Ingredient(123L, "Eggs", true),
                                        new Ingredient(124L, "Tomato", false),
                                        new Ingredient(125L, "Garlic", false)
                                ))
                                .imageUrl("../pizza/margherita")
                                .build(),

                        Meal.builder()
                                .name("Pulled Pork")
                                .status(AVAILABLE)
                                .price(BigDecimal.valueOf(32.00))
                                .ingredients(Set.of(
                                        new Ingredient(123L, "Pork", false),
                                        new Ingredient(124L, "Butter", true)
                                ))
                                .imageUrl("../meat/pulled-pork")
                                .build()
                )),
                new Order(124L, "ijkl-mnop", SUBMITTED, BigDecimal.valueOf(92.00), List.of(
                        Meal.builder()
                                .name("Pizza Margherita")
                                .status(AVAILABLE)
                                .price(BigDecimal.valueOf(35.00))
                                .ingredients(Set.of(
                                        new Ingredient(123L, "Eggs", true),
                                        new Ingredient(124L, "Tomato", false),
                                        new Ingredient(125L, "Garlic", false)
                                ))
                                .imageUrl("../pizza/margherita")
                                .build(),

                        Meal.builder()
                                .name("Pulled Pork")
                                .status(AVAILABLE)
                                .price(BigDecimal.valueOf(32.00))
                                .ingredients(Set.of(
                                        new Ingredient(123L, "Pork", false),
                                        new Ingredient(124L, "Butter", true)
                                ))
                                .imageUrl("../meat/pulled-pork")
                                .build()
                ))
        );

        List<OrderResponse> orderResponses = mapStructMapper.ordersToOrderResponses(orders);

        assertThat(orderResponses)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(orders);
    }
}