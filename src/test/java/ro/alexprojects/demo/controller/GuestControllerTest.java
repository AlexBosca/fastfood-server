package ro.alexprojects.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ro.alexprojects.demo.dto.meal.MealResponse;
import ro.alexprojects.demo.dto.order.OrderRequest;
import ro.alexprojects.demo.dto.order.OrderResponse;
import ro.alexprojects.demo.mappers.MapStructMapper;
import ro.alexprojects.demo.mappers.MapStructMapperImpl;
import ro.alexprojects.demo.model.ingredient.Ingredient;
import ro.alexprojects.demo.model.meal.Meal;
import ro.alexprojects.demo.model.order.Order;
import ro.alexprojects.demo.service.GuestService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ro.alexprojects.demo.model.meal.MealStatus.AVAILABLE;
import static ro.alexprojects.demo.model.order.OrderStatus.DONE;
import static ro.alexprojects.demo.model.order.OrderStatus.SUBMITTED;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = GuestController.class)
@ComponentScan("ro.alexprojects.demo.mappers")
class GuestControllerTest {

    @Autowired
    private MapStructMapper mapStructMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GuestService guestService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return menu")
    void shouldReturnMenu() throws Exception {
        List<Meal> meals = List.of(
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
        );

        List<MealResponse> expectedResponses = mapStructMapper.mealsToMealResponses(meals);

        when(guestService.getMenu()).thenReturn(meals);

        mockMvc.perform(get("/guest/menu/"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(expectedResponses))
                );
    }

    @Test
    @DisplayName("Should return order by orderID when it exists")
    void shouldReturnOrderIfExists() throws Exception {
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

        OrderResponse expectedResponse = mapStructMapper.orderToOrderResponse(order);

        when(guestService.getOrder("abcd-efgh")).thenReturn(order);

        mockMvc.perform(get("/guest/order/{orderID}", "abcd-efgh"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(expectedResponse))
                );
    }

    @Test
    @DisplayName("Should have an error status when orderID was not found")
    void shouldHaveErrorStatusWhenOrderToGetDoesNotExists() {

    }

    @Test
    @DisplayName("Should place order")
    void shouldPlaceOrder() throws Exception {
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

        mockMvc.perform(post("/guest/order/").contentType(APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(orderRequest))
                ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should pay order if exists")
    void shouldPayOrderIfExists() throws Exception {
        Order order = new Order(123L, "abcd-efgh", DONE, BigDecimal.valueOf(92.00), List.of(
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

        mockMvc.perform(put("/guest/pay/{orderID}", "abcd-efgh"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should have an error status when order to pay was not found")
    void shouldHaveErrorStatusWhenOrderToPayDoesNotExists() {
        
    }
}