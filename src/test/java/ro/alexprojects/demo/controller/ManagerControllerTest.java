package ro.alexprojects.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.alexprojects.demo.dto.ingredient.IngredientRequest;
import ro.alexprojects.demo.dto.ingredient.IngredientResponse;
import ro.alexprojects.demo.dto.meal.MealRequest;
import ro.alexprojects.demo.mappers.MapStructMapper;
import ro.alexprojects.demo.model.ingredient.Ingredient;
import ro.alexprojects.demo.model.meal.Meal;
import ro.alexprojects.demo.service.ManagerService;

import java.math.BigDecimal;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ro.alexprojects.demo.model.meal.MealStatus.AVAILABLE;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ManagerController.class)
@ComponentScan("ro.alexprojects.demo.mappers")
class ManagerControllerTest {

    @Autowired
    private MapStructMapper mapStructMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ManagerService managerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return all ingredients")
    void shouldReturnAllIngredients() throws Exception {
        Set<Ingredient> ingredients = Set.of(
                new Ingredient(123L, "Eggs", true),
                new Ingredient(124L, "Tomato", false),
                new Ingredient(125L, "Garlic", false)
        );

        Set<IngredientResponse> expectedResponses = mapStructMapper.ingredientsToIngredientResponses(ingredients);

        when(managerService.getAllIngredients()).thenReturn(ingredients);

        mockMvc.perform(get("/manager/ingredients/"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(expectedResponses))
                );
    }

    @Test
    @DisplayName("Should create ingredient")
    void shouldCreateIngredient() throws Exception {
        Ingredient ingredient = new Ingredient(123L, "Eggs", true);

        IngredientRequest ingredientRequest = mapStructMapper.ingredientToIngredientRequest(ingredient);

        mockMvc.perform(post("/manager/ingredient/").contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(ingredientRequest))
            ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should create meal")
    void shouldCreateMeal() throws Exception {
        Meal meal = new Meal(123L, "Pizza Margherita", AVAILABLE, BigDecimal.valueOf(35.00), Set.of(new Ingredient(
                123L,
                "Eggs",
                true
        )), "../pizza/margherita");

        MealRequest mealRequest = mapStructMapper.mealToMealRequest(meal);

        mockMvc.perform(post("/manager/meal/").contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(mealRequest))
            ).andExpect(status().isCreated());
    }
}