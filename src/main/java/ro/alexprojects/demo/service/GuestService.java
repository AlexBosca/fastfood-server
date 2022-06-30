package ro.alexprojects.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.alexprojects.demo.dto.order.OrderRequest;
import ro.alexprojects.demo.exceptions.OrderNotFoundException;
import ro.alexprojects.demo.model.meal.Meal;
import ro.alexprojects.demo.model.order.Order;
import ro.alexprojects.demo.repository.MealRepository;
import ro.alexprojects.demo.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

import static ro.alexprojects.demo.model.order.OrderStatus.PAID;
import static ro.alexprojects.demo.model.order.OrderStatus.PREPARING;

@Service
public class GuestService extends GenericService {

    @Autowired
    public GuestService(
            MealRepository mealRepository,
            OrderRepository orderRepository) {
        super(mealRepository, orderRepository);
    }

    public List<Meal> getMenu() {
        return mealRepository.findAll();
    }

    public void placeOrder(Order order) {
        orderRepository.save(order);
    }

    public void payOrder(String orderID) {
        orderRepository.findByOrderID(orderID)
                .orElseThrow(() -> new OrderNotFoundException(orderID));

        orderRepository.updateStatusByOrderID(PAID, orderID);
    }

    public Order getOrder(String orderID) {
        return orderRepository.findByOrderID(orderID)
                .orElseThrow(() -> new OrderNotFoundException(orderID));
    }
}
