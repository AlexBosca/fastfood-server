package ro.alexprojects.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ro.alexprojects.demo.model.ingredient.Ingredient;
import ro.alexprojects.demo.model.meal.Meal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static ro.alexprojects.demo.model.meal.MealStatus.AVAILABLE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class MealRepositoryTest extends RepositoryTest {

    @Autowired
    private MealRepository mealRepository;

    @BeforeEach
    void setUp() {
        mealRepository.save(
                new Meal(
                        null,
                        "Pizza Margherita",
                        AVAILABLE,
                        BigDecimal.valueOf(35.00),
                        null,
                        "../pizza/margherita")
        );

        mealRepository.save(
                new Meal(
                        null,
                        "Pulled Pork",
                        AVAILABLE,
                        BigDecimal.valueOf(32.00),
                        null,
                        "../meat/pulled-pork")
        );

        mealRepository.save(
                new Meal(
                        null,
                        "Chicken Noodles",
                        AVAILABLE,
                        BigDecimal.valueOf(25.00),
                        null,
                        "../noodles/chicken")
        );
    }

    @AfterEach
    void tearDown() {
        mealRepository.deleteAll();
    }

    @Test
    @DisplayName("Should save meal")
    void shouldSaveMeal() {
        Meal expectedMeal = new Meal(
                null,
                "Mac and Cheese",
                AVAILABLE,
                BigDecimal.valueOf(27.00),
                null,
                "../pasta/mac-and-cheese"
        );

        Meal actualMeal = mealRepository.save(expectedMeal);

        assertThat(actualMeal)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedMeal);
    }

    @Test
    @DisplayName("Should return all meals")
    void shouldReturnAllMeals() {
        List<Meal> expectedMeals = List.of(
                Meal.builder()
                        .name("Pizza Margherita")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(35.00))
                        .ingredients(null)
                        .imageUrl("../pizza/margherita")
                        .build(),

                Meal.builder()
                        .name("Pulled Pork")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(32.00))
                        .ingredients(null)
                        .imageUrl("../meat/pulled-pork")
                        .build(),

                Meal.builder()
                        .name("Chicken Noodles")
                        .status(AVAILABLE)
                        .price(BigDecimal.valueOf(25.00))
                        .ingredients(null)
                        .imageUrl("../noodles/chicken")
                        .build()
        );

        List<Meal> actualMeals = mealRepository.findAll();

        assertThat(actualMeals)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedMeals);
    }
}