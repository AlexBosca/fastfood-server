package ro.alexprojects.demo.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ro.alexprojects.demo.model.meal.Meal;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column
    private String orderID;

    @Enumerated(STRING)
    private OrderStatus status;

    @Basic
    @Column
    private BigDecimal totalPrice;

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "meal_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<Meal> meals;
}
