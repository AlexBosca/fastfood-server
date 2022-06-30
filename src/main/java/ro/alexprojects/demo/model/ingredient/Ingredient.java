package ro.alexprojects.demo.model.ingredient;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity(name = "ingredients")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id")
    private Long id;
    private String name;
    @Getter(AccessLevel.NONE)
    private Boolean allergen;

    public Boolean isAllergen() {
        return allergen;
    }
}
