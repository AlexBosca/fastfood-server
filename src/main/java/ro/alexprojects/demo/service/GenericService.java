package ro.alexprojects.demo.service;

import lombok.AllArgsConstructor;
import ro.alexprojects.demo.repository.MealRepository;
import ro.alexprojects.demo.repository.OrderRepository;

@AllArgsConstructor
public abstract class GenericService {
    protected final MealRepository mealRepository;
    protected final OrderRepository orderRepository;
}
