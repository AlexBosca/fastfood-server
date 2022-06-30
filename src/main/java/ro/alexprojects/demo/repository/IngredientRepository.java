package ro.alexprojects.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.alexprojects.demo.model.ingredient.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
