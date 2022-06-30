package ro.alexprojects.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.alexprojects.demo.model.ingredient.Ingredient;
import ro.alexprojects.demo.model.meal.Meal;
import ro.alexprojects.demo.repository.IngredientRepository;
import ro.alexprojects.demo.repository.MealRepository;
import ro.alexprojects.demo.repository.OrderRepository;

import java.util.Set;

@Service
public class ManagerService extends GenericService{
    private final IngredientRepository ingredientRepository;

    @Autowired
    public ManagerService(
            MealRepository mealRepository,
            OrderRepository orderRepository,
            IngredientRepository ingredientRepository) {
        super(mealRepository, orderRepository);

        this.ingredientRepository = ingredientRepository;
    }

    public void createIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }

    public Set<Ingredient> getAllIngredients() {
        return Set.copyOf(ingredientRepository.findAll());
    }

    public void createMeal(Meal meal) {
        mealRepository.save(meal);
    }
}
