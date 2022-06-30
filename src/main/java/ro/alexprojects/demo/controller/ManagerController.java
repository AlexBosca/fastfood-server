package ro.alexprojects.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.alexprojects.demo.dto.ingredient.IngredientRequest;
import ro.alexprojects.demo.dto.ingredient.IngredientResponse;
import ro.alexprojects.demo.dto.meal.MealRequest;
import ro.alexprojects.demo.mappers.MapStructMapper;
import ro.alexprojects.demo.service.ManagerService;

import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/manager")
@AllArgsConstructor
public class ManagerController {
    private final ManagerService managerService;
    private final MapStructMapper mapStructMapper;

    @PostMapping(path = "/ingredient")
    public ResponseEntity<Void> createIngredient(@RequestBody IngredientRequest ingredientRequest) {
        managerService.createIngredient(
                mapStructMapper.ingredientRequestToIngredient(ingredientRequest)
        );

        return new ResponseEntity<>(CREATED);
    }

    @GetMapping(path = "/ingredients")
    public ResponseEntity<Set<IngredientResponse>> getAllIngredients() {
        return new ResponseEntity<>(
                mapStructMapper.ingredientsToIngredientResponses(managerService.getAllIngredients()),
                OK
        );
    }

    @PostMapping(path = "/meal")
    public ResponseEntity<Void> createMeal(@RequestBody MealRequest mealRequest) {
        managerService.createMeal(
                mapStructMapper.mealRequestToMeal(mealRequest)
        );

        return new ResponseEntity<>(CREATED);
    }
}
