package ro.alexprojects.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.alexprojects.demo.dto.order.OrderResponse;
import ro.alexprojects.demo.exceptions.OrderNotFoundException;
import ro.alexprojects.demo.model.order.Order;
import ro.alexprojects.demo.repository.MealRepository;
import ro.alexprojects.demo.repository.OrderRepository;

import java.util.List;

import static ro.alexprojects.demo.model.order.OrderStatus.DONE;
import static ro.alexprojects.demo.model.order.OrderStatus.PREPARING;

@Service
public class CookService extends GenericService{

    @Autowired
    public CookService(MealRepository mealRepository, OrderRepository orderRepository) {
        super(mealRepository, orderRepository);
    }

    public void prepareOrder(String orderID) {
        orderRepository.findByOrderID(orderID)
                .orElseThrow(() -> new OrderNotFoundException(orderID));

        orderRepository.updateStatusByOrderID(PREPARING, orderID);
    }

    public void finishOrder(String orderID) {
        orderRepository.findByOrderID(orderID)
                .orElseThrow(() -> new OrderNotFoundException(orderID));

        orderRepository.updateStatusByOrderID(DONE, orderID);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
