package ro.alexprojects.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.alexprojects.demo.model.ingredient.Ingredient;
import ro.alexprojects.demo.model.meal.Meal;
import ro.alexprojects.demo.repository.IngredientRepository;
import ro.alexprojects.demo.repository.MealRepository;
import ro.alexprojects.demo.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static ro.alexprojects.demo.model.meal.MealStatus.AVAILABLE;

@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MealRepository mealRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Captor
    private ArgumentCaptor<Ingredient> ingredientArgumentCaptor;

    @Captor
    private ArgumentCaptor<Meal> mealArgumentCaptor;

    private ManagerService managerService;

    @BeforeEach
    void setUp() {
        managerService = new ManagerService(
                mealRepository,
                orderRepository,
                ingredientRepository
        );
    }

    @Test
    @DisplayName("Should create ingredient")
    void shouldCreateIngredient() {
        Ingredient expectedIngredient = new Ingredient(
                123L,
                "Eggs",
                true
        );

        managerService.createIngredient(expectedIngredient);

        verify(ingredientRepository, times(1))
                .save(ingredientArgumentCaptor.capture());

        Ingredient capturedIngredient = ingredientArgumentCaptor.getValue();

        assertThat(capturedIngredient).isEqualTo(expectedIngredient);
    }

    @Test
    @DisplayName("Should return a not empty set when exist ingredients")
    void shouldReturnMealsListInsideMenu() {
        when(ingredientRepository.findAll()).thenReturn(
                List.of(
                        new Ingredient(123L, "Eggs", true),
                        new Ingredient(124L, "Tomato", false),
                        new Ingredient(125L, "Garlic", false)
                )
        );

        assertThat(managerService.getAllIngredients()).isNotEmpty();
    }

    @Test
    @DisplayName("Should return an empty list when doesn't exist orders")
    void shouldReturnEmptyMealsListInsideMenu() {
        when(ingredientRepository.findAll()).thenReturn(List.of());

        assertThat(managerService.getAllIngredients()).isEmpty();
    }

    @Test
    @DisplayName("Should create meal")
    void shouldCreateMeal() {
        Meal expectedMeal = new Meal(123L,
                "Pizza Margherita",
                AVAILABLE,
                BigDecimal.valueOf(35.00),
                null,
                "../pizza/margherita"
        );

        managerService.createMeal(expectedMeal);

        verify(mealRepository, times(1))
                .save(mealArgumentCaptor.capture());

        Meal capturedMeal = mealArgumentCaptor.getValue();

        assertThat(capturedMeal).isEqualTo(expectedMeal);
    }
}