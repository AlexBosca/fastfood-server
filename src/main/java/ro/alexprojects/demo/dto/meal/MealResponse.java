package ro.alexprojects.demo.dto.meal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ro.alexprojects.demo.dto.ingredient.IngredientResponse;
import ro.alexprojects.demo.model.meal.MealStatus;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
public class MealResponse {
    @JsonProperty("name")
    private String name;

    @JsonProperty("status")
    private MealStatus status;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("ingredients")
    private Set<IngredientResponse> ingredients;

    @JsonProperty("imageUrl")
    private String imageUrl;
}
