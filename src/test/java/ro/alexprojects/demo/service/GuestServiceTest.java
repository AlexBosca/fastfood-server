package ro.alexprojects.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
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
import static ro.alexprojects.demo.model.order.OrderStatus.PAID;
import static ro.alexprojects.demo.model.order.OrderStatus.SUBMITTED;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MealRepository mealRepository;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @Captor
    ArgumentCaptor<OrderStatus> orderStatusArgumentCaptor;

    @Captor
    ArgumentCaptor<String> orderIDArgumentCaptor;

    private GuestService guestService;

    @BeforeEach
    void setUp() {
        guestService = new GuestService(
                mealRepository,
                orderRepository
        );
    }

    @Test
    @DisplayName("Should return a not empty list when menu is not empty")
    void shouldReturnMealsListInsideMenu() {
        when(mealRepository.findAll()).thenReturn(
                List.of(
                        new Meal(123L, "Pizza Margherita", AVAILABLE, BigDecimal.valueOf(35.00), null, "../pizza/margherita"),
                        new Meal(123L, "Pulled Pork", AVAILABLE, BigDecimal.valueOf(32.0), null, "../meat/pulled-pork"),
                        new Meal(123L, "Chicken Noodles", AVAILABLE, BigDecimal.valueOf(25.00), null, "../noodles/chicken")
                )
        );

        assertThat(guestService.getMenu()).isNotEmpty();
    }

    @Test
    @DisplayName("Should return an empty list when menu is empty")
    void shouldReturnEmptyMealsListInsideMenu() {
        when(mealRepository.findAll()).thenReturn(List.of());

        assertThat(guestService.getMenu()).isEmpty();
    }

    @Test
    @DisplayName("Should save order")
    void shouldSaveOrder() {
        guestService.placeOrder(
                new Order(123L, "abcd-efgh", SUBMITTED, BigDecimal.valueOf(92.00), List.of(
                        new Meal(
                                123L,
                                "Pizza Margherita",
                                AVAILABLE,
                                BigDecimal.valueOf(35.00),
                                null,
                                "../pizza/margherita"
                        ),
                        new Meal(
                                123L,
                                "Pulled Pork",
                                AVAILABLE,
                                BigDecimal.valueOf(32.0),
                                null,
                                "../meat/pulled-pork"
                        ),
                        new Meal(
                                123L,
                                "Chicken Noodles",
                                AVAILABLE,
                                BigDecimal.valueOf(25.00),
                                null,
                                "../noodles/chicken"
                        )
                ))
        );

        verify(orderRepository, times(1)).save(orderArgumentCaptor.capture());

        Order capturedOrder = orderArgumentCaptor.getValue();

        assertThat(capturedOrder.getId()).isEqualTo(123L);
        assertThat(capturedOrder.getOrderID()).isEqualTo("abcd-efgh");
        assertThat(capturedOrder.getStatus()).isEqualTo(SUBMITTED);
        assertThat(capturedOrder.getTotalPrice()).isEqualTo(BigDecimal.valueOf(92.00));
        assertThat(capturedOrder.getMeals()).isEqualTo(
                List.of(
                        new Meal(123L, "Pizza Margherita", AVAILABLE, BigDecimal.valueOf(35.00), null, "../pizza/margherita"),
                        new Meal(123L, "Pulled Pork", AVAILABLE, BigDecimal.valueOf(32.0), null, "../meat/pulled-pork"),
                        new Meal(123L, "Chicken Noodles", AVAILABLE, BigDecimal.valueOf(25.00), null, "../noodles/chicken")
                )
        );
    }

    @Test
    @DisplayName("Should pay order by orderID when it exists")
    void shouldPayOrderIfOrderExists() {
        Order expectedOrder = new Order(123L, "abcd-efgh", SUBMITTED, BigDecimal.valueOf(92.00), List.of(
                new Meal(
                        123L,
                        "Pizza Margherita",
                        AVAILABLE,
                        BigDecimal.valueOf(35.00),
                        null,
                        "../pizza/margherita"
                ),
                new Meal(
                        123L,
                        "Pulled Pork",
                        AVAILABLE,
                        BigDecimal.valueOf(32.0),
                        null,
                        "../meat/pulled-pork"
                ),
                new Meal(
                        123L,
                        "Chicken Noodles",
                        AVAILABLE,
                        BigDecimal.valueOf(25.00),
                        null,
                        "../noodles/chicken"
                )
        ));

        when(orderRepository.findByOrderID("abcd-efgh")).thenReturn(Optional.of(expectedOrder));

        guestService.payOrder("abcd-efgh");

        verify(orderRepository, times(1)).updateStatusByOrderID(
                orderStatusArgumentCaptor.capture(),
                orderIDArgumentCaptor.capture()
        );

        assertThat(orderIDArgumentCaptor.getValue()).isEqualTo(expectedOrder.getOrderID());
        assertThat(orderStatusArgumentCaptor.getValue()).isEqualTo(PAID);
    }

    @Test
    @DisplayName("Should throw an exception when try to pay an order that doesn't exists")
    void shouldThrowExceptionIfOrderToPayDoesNotExists() {
        when(orderRepository.findByOrderID("abcd-efgh")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            guestService.payOrder("abcd-efgh");
        }).isInstanceOf(OrderNotFoundException.class)
                .hasMessage("No order found with ID: abcd-efgh");
    }

    @Test
    @DisplayName("Should return the order by orderID when it exists")
    void shouldGetOrderIfExists() {
        Order expectedOrder = new Order(123L, "abcd-efgh", SUBMITTED, BigDecimal.valueOf(92.00), List.of(
                new Meal(
                        123L,
                        "Pizza Margherita",
                        AVAILABLE,
                        BigDecimal.valueOf(35.00),
                        null,
                        "../pizza/margherita"
                ),
                new Meal(
                        123L,
                        "Pulled Pork",
                        AVAILABLE,
                        BigDecimal.valueOf(32.0),
                        null,
                        "../meat/pulled-pork"
                ),
                new Meal(
                        123L,
                        "Chicken Noodles",
                        AVAILABLE,
                        BigDecimal.valueOf(25.00),
                        null,
                        "../noodles/chicken"
                )
        ));

        when(orderRepository.findByOrderID("abcd-efgh")).thenReturn(Optional.of(expectedOrder));

        Order actualOrder = guestService.getOrder("abcd-efgh");

        assertThat(actualOrder).isEqualTo(expectedOrder);
    }

    @Test
    @DisplayName("Should throw an exception when try to get an order that doesn't exists")
    void shouldThrowExceptionIfOrderToGetDoesNotExists() {
        when(orderRepository.findByOrderID("abcd-efgh")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            guestService.getOrder("abcd-efgh");
        }).isInstanceOf(OrderNotFoundException.class)
                .hasMessage("No order found with ID: abcd-efgh");
    }
}