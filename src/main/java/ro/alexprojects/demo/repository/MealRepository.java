package ro.alexprojects.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.alexprojects.demo.model.meal.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
}
