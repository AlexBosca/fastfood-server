package ro.alexprojects.demo.model.meal;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ro.alexprojects.demo.model.ingredient.Ingredient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity(name = "meals")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column
    private String name;

    @Enumerated(STRING)
    private MealStatus status;

    @Basic
    @Column
    private BigDecimal price;

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "ingredient_meal",
            joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> ingredients;

    @Basic
    @Column
    private String imageUrl;

    public Set<Ingredient> getAllergens() {
        return ingredients.stream().filter(Ingredient::isAllergen).collect(Collectors.toSet());
    }
}
