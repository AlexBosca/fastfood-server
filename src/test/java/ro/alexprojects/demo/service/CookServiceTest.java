package ro.alexprojects.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.alexprojects.demo.exceptions.OrderNotFoundException;
import ro.alexprojects.demo.model.meal.Meal;
import ro.alexprojects.demo.model.order.Order;
import ro.alexprojects.demo.model.order.OrderStatus;
import ro.alexprojects.demo.repository.MealRepository;
import ro.alexprojects.demo.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static ro.alexprojects.demo.model.meal.MealStatus.AVAILABLE;
import static ro.alexprojects.demo.model.order.OrderStatus.*;

@ExtendWith(MockitoExtension.class)
class CookServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MealRepository mealRepository;

    @Captor
    ArgumentCaptor<OrderStatus> orderStatusArgumentCaptor;

    @Captor
    ArgumentCaptor<String> orderIDArgumentCaptor;

    private CookService cookService;

    @BeforeEach
    void setUp() {
        cookService = new CookService(
                mealRepository,
                orderRepository
        );
    }

    @Test
    @DisplayName("Should prepare order by orderID when it exists")
    void shouldPrepareOrderIfExists() {
        Order expectedOrder = new Order(123L, "abcd-efgh", PREPARING, BigDecimal.valueOf(92.00), List.of(
                new Meal(123L, "Pizza Margherita", AVAILABLE, BigDecimal.valueOf(35.00), null, "../pizza/margherita"),
                new Meal(123L, "Pulled Pork", AVAILABLE, BigDecimal.valueOf(32.0), null, "../meat/pulled-pork"),
                new Meal(123L, "Chicken Noodles", AVAILABLE, BigDecimal.valueOf(25.00), null, "../noodles/chicken")
        ));

        when(orderRepository.findByOrderID("abcd-efgh")).thenReturn(Optional.of(expectedOrder));

        cookService.prepareOrder("abcd-efgh");

        verify(orderRepository, times(1)).updateStatusByOrderID(
                orderStatusArgumentCaptor.capture(),
                orderIDArgumentCaptor.capture()
        );

        assertThat(orderIDArgumentCaptor.getValue()).isEqualTo(expectedOrder.getOrderID());
        assertThat(orderStatusArgumentCaptor.getValue()).isEqualTo(expectedOrder.getStatus());
    }

    @Test
    @DisplayName("Should throw an exception when try to prepare an order that doesn't exists")
    void shouldThrowExceptionIfOrderToPrepareDoesNotExists() {
        when(orderRepository.findByOrderID("abcd-efgh")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            cookService.prepareOrder("abcd-efgh");
        }).isInstanceOf(OrderNotFoundException.class)
                .hasMessage("No order found with ID: abcd-efgh");
    }

    @Test
    @DisplayName("Should finish order by orderID when it exists")
    void shouldFinishOrderIfExists() {
        Order expectedOrder = new Order(123L, "abcd-efgh", DONE, BigDecimal.valueOf(92.00), List.of(
                new Meal(123L, "Pizza Margherita", AVAILABLE, BigDecimal.valueOf(35.00), null, "../pizza/margherita"),
                new Meal(123L, "Pulled Pork", AVAILABLE, BigDecimal.valueOf(32.0), null, "../meat/pulled-pork"),
                new Meal(123L, "Chicken Noodles", AVAILABLE, BigDecimal.valueOf(25.00), null, "../noodles/chicken")
        ));

        when(orderRepository.findByOrderID("abcd-efgh")).thenReturn(Optional.of(expectedOrder));

        cookService.finishOrder("abcd-efgh");

        verify(orderRepository, times(1)).updateStatusByOrderID(
                orderStatusArgumentCaptor.capture(),
                orderIDArgumentCaptor.capture()
        );

        assertThat(orderIDArgumentCaptor.getValue()).isEqualTo(expectedOrder.getOrderID());
        assertThat(orderStatusArgumentCaptor.getValue()).isEqualTo(expectedOrder.getStatus());
    }

    @Test
    @DisplayName("Should throw an exception when try to finish an order that doesn't exists")
    void shouldThrowExceptionIfOrderToFinishDoesNotExists() {
        when(orderRepository.findByOrderID("abcd-efgh")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            cookService.prepareOrder("abcd-efgh");
        }).isInstanceOf(OrderNotFoundException.class)
                .hasMessage("No order found with ID: abcd-efgh");
    }

    @Test
    @DisplayName("Should return a not empty list when exist orders")
    void shouldReturnMealsListInsideMenu() {
        when(orderRepository.findAll()).thenReturn(
                List.of(
                        new Order(123L, "abcd-efgh", PREPARING, BigDecimal.valueOf(92.00), List.of(
                                new Meal(123L, "Pizza Margherita", AVAILABLE, BigDecimal.valueOf(35.00), null, "../pizza/margherita"),
                                new Meal(123L, "Pulled Pork", AVAILABLE, BigDecimal.valueOf(32.0), null, "../meat/pulled-pork"),
                                new Meal(123L, "Chicken Noodles", AVAILABLE, BigDecimal.valueOf(25.00), null, "../noodles/chicken")
                        )),
                        new Order(123L, "ijkl-mnop", DONE, BigDecimal.valueOf(92.00), List.of(
                                new Meal(123L, "Pizza Margherita", AVAILABLE, BigDecimal.valueOf(35.00), null, "../pizza/margherita"),
                                new Meal(123L, "Pulled Pork", AVAILABLE, BigDecimal.valueOf(32.0), null, "../meat/pulled-pork"),
                                new Meal(123L, "Chicken Noodles", AVAILABLE, BigDecimal.valueOf(25.00), null, "../noodles/chicken")
                        ))
                )
        );

        assertThat(cookService.getOrders()).isNotEmpty();
    }

    @Test
    @DisplayName("Should return an empty list when doesn't exist orders")
    void shouldReturnEmptyMealsListInsideMenu() {
        when(orderRepository.findAll()).thenReturn(List.of());

        assertThat(cookService.getOrders()).isEmpty();
    }
}