package ro.alexprojects.demo.dto.meal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ro.alexprojects.demo.dto.ingredient.IngredientRequest;
import ro.alexprojects.demo.model.meal.MealStatus;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
public class MealRequest {
    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("status")
    @NotNull
    private MealStatus status;

    @JsonProperty("price")
    @NotNull
    private BigDecimal price;

    @JsonProperty("ingredients")
    @NotNull
    private Set<IngredientRequest> ingredients;

    @JsonProperty("imageUrl")
    private String imageUrl;
}
