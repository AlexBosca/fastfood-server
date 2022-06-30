package ro.alexprojects.demo.dto.ingredient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IngredientRequest {
    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("allergen")
    @NotNull
    @Getter(AccessLevel.NONE)
    private Boolean allergen;

    public Boolean isAllergen() {
        return allergen;
    }
}
