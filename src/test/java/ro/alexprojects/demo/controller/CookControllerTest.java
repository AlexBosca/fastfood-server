package ro.alexprojects.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import ro.alexprojects.demo.dto.order.OrderRequest;
import ro.alexprojects.demo.dto.order.OrderResponse;
import ro.alexprojects.demo.mappers.MapStructMapper;
import ro.alexprojects.demo.model.ingredient.Ingredient;
import ro.alexprojects.demo.model.meal.Meal;
import ro.alexprojects.demo.model.order.Order;
import ro.alexprojects.demo.service.CookService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ro.alexprojects.demo.model.meal.MealStatus.AVAILABLE;
import static ro.alexprojects.demo.model.order.OrderStatus.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = CookController.class)
@ComponentScan("ro.alexprojects.demo.mappers")
class CookControllerTest {

    @Autowired
    private MapStructMapper mapStructMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CookService cookService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should prepare order by orderID if exists")
    void shouldPrepareOrderIfExists() throws Exception {
        Order order = new Order(123L, "abcd-efgh", SUBMITTED, BigDecimal.valueOf(92.00), List.of(
                new Meal(null, "Pizza Margherita", AVAILABLE, BigDecimal.valueOf(35.00), Set.of(new Ingredient(
                        123L,
                        "Eggs",
                        true
                )), "../pizza/margherita"),
                new Meal(null, "Pulled Pork", AVAILABLE, BigDecimal.valueOf(32.0), Set.of(new Ingredient(
                        123L,
                        "Eggs",
                        true
                )), "../meat/pulled-pork")
        ));

        OrderRequest orderRequest = mapStructMapper.orderToOrderRequest(order);

        mockMvc.perform(put("/cook/prepare/{orderID}", "abcd-efgh"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should have an error status when order to prepare was not found")
    void shouldHaveErrorStatusWhenOrderToPrepareDoesNotExists() {

    }

    @Test
    @DisplayName("Should finish order by orderID if exists")
    void shouldFinishOrderIfExists() throws Exception {
        Order order = new Order(123L, "abcd-efgh", PREPARING, BigDecimal.valueOf(92.00), List.of(
                new Meal(null, "Pizza Margherita", AVAILABLE, BigDecimal.valueOf(35.00), Set.of(new Ingredient(
                        123L,
                        "Eggs",
                        true
                )), "../pizza/margherita"),
                new Meal(null, "Pulled Pork", AVAILABLE, BigDecimal.valueOf(32.0), Set.of(new Ingredient(
                        123L,
                        "Eggs",
                        true
                )), "../meat/pulled-pork")
        ));

        OrderRequest orderRequest = mapStructMapper.orderToOrderRequest(order);

        mockMvc.perform(put("/cook/finish/{orderID}", "abcd-efgh"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should have an error status when order to finish was not found")
    void shouldHaveErrorStatusWhenOrderToFinishDoesNotExists() {

    }

    @Test
    @DisplayName("Should return all orders")
    void shouldReturnAllOrders() throws Exception {
        List<Order> orders = List.of(
                new Order(123L, "abcd-efgh", PREPARING, BigDecimal.valueOf(92.00), List.of(
                        new Meal(123L, "Pizza Margherita", AVAILABLE, BigDecimal.valueOf(35.00), Set.of(new Ingredient(
                                123L,
                                "Mozzarella",
                                true
                        )), "../pizza/margherita"),
                        new Meal(123L, "Pulled Pork", AVAILABLE, BigDecimal.valueOf(32.0), Set.of(new Ingredient(
                                124L,
                                "Pork",
                                false
                        )), "../meat/pulled-pork"),
                        new Meal(123L, "Chicken Noodles", AVAILABLE, BigDecimal.valueOf(25.00), Set.of(new Ingredient(
                                125L,
                                "Chicken",
                                false
                        )), "../noodles/chicken")
                )),
                new Order(124L, "abcd-efgh", PREPARING, BigDecimal.valueOf(92.00), List.of(
                        new Meal(123L, "Pizza Margherita", AVAILABLE, BigDecimal.valueOf(35.00), Set.of(new Ingredient(
                                123L,
                                "Mozzarella",
                                true
                        )), "../pizza/margherita"),
                        new Meal(123L, "Pulled Pork", AVAILABLE, BigDecimal.valueOf(32.0), Set.of(new Ingredient(
                                124L,
                                "Pork",
                                false
                        )), "../meat/pulled-pork"),
                        new Meal(123L, "Chicken Noodles", AVAILABLE, BigDecimal.valueOf(25.00), Set.of(new Ingredient(
                                125L,
                                "Chicken",
                                false
                        )), "../noodles/chicken")
                ))
        );

        List<OrderResponse> expectedResponses = mapStructMapper.ordersToOrderResponses(orders);

        when(cookService.getOrders()).thenReturn(orders);

        mockMvc.perform(get("/cook/orders/"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(expectedResponses))
                );
    }
}