package ro.alexprojects.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ro.alexprojects.demo.model.ingredient.Ingredient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class IngredientRepositoryTest extends RepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    void setUp() {
        ingredientRepository.save(new Ingredient(
                null,
                "Milk",
                true
        ));

        ingredientRepository.save(new Ingredient(
                null,
                "Flour",
                false
        ));

        ingredientRepository.save(new Ingredient(
                null,
                "Cheese",
                true
        ));

        ingredientRepository.save(new Ingredient(
                null,
                "Tomatoes",
                false
        ));
    }

    @AfterEach
    void tearDown() {
        ingredientRepository.deleteAll();
    }

    @Test
    @DisplayName("Should save ingredient")
    void shouldSaveIngredient() {
        Ingredient expectedIngredient = new Ingredient(
                null,
                "Eggs",
                true
        );

        Ingredient actualIngredient = ingredientRepository.save(expectedIngredient);

        assertThat(actualIngredient)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedIngredient);
    }

    @Test
    @DisplayName("Should return all ingredients")
    void shouldReturnAllIngredients() {
        List<Ingredient> expectedIngredients = List.of(
                new Ingredient(null, "Eggs", true),
                new Ingredient(null, "Milk", true),
                new Ingredient(null, "Flour", false),
                new Ingredient(null, "Cheese", true),
                new Ingredient(null, "Tomatoes", false)
        );

        List<Ingredient> actualIngredients = ingredientRepository.findAll();

        assertThat(actualIngredients)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedIngredients);
    }
}