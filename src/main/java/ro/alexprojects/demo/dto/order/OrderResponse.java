package ro.alexprojects.demo.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ro.alexprojects.demo.dto.meal.MealResponse;
import ro.alexprojects.demo.model.order.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderResponse {
    @JsonProperty("orderID")
    @NotNull
    private String orderID;

    @JsonProperty("status")
    private OrderStatus status;

    @JsonProperty("totalPrice")
    private BigDecimal totalPrice;

    @JsonProperty("meals")
    private List<MealResponse> meals;
}
