package ro.alexprojects.demo.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ro.alexprojects.demo.dto.meal.MealRequest;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderRequest {
    @JsonProperty("orderID")
    @NotNull
    private String orderID;

    @JsonProperty("meals")
    @NotNull
    private List<MealRequest> meals;
}
