package ro.alexprojects.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ro.alexprojects.demo.model.order.Order;
import ro.alexprojects.demo.model.order.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static ro.alexprojects.demo.model.order.OrderStatus.PREPARING;
import static ro.alexprojects.demo.model.order.OrderStatus.SUBMITTED;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class OrderRepositoryTest extends RepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.save(
                Order.builder()
                        .orderID("abcd-efgh")
                        .status(SUBMITTED)
                        .totalPrice(BigDecimal.valueOf(100.00))
                        .build()
        );

        orderRepository.save(
                Order.builder()
                        .orderID("ijkl-mnop")
                        .status(SUBMITTED)
                        .totalPrice(BigDecimal.valueOf(150.00))
                        .build()
        );

        orderRepository.save(
                Order.builder()
                        .orderID("qrst-uvwx")
                        .status(SUBMITTED)
                        .totalPrice(BigDecimal.valueOf(80.00))
                        .build()
        );
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    @DisplayName("Should save order")
    void shouldSaveOrder() {
        Order expectedOrder = Order.builder()
                .orderID("azby-cxdw")
                .status(SUBMITTED)
                .totalPrice(BigDecimal.valueOf(80.00))
                .build();

        Order actualOrder = orderRepository.save(expectedOrder);

        assertThat(actualOrder)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedOrder);
    }

    @Test
    @DisplayName("Should return all orders")
    void shouldReturnAllOrders() {
        List<Order> expectedOrders = List.of(
                Order.builder()
                        .orderID("abcd-efgh")
                        .status(SUBMITTED)
                        .totalPrice(BigDecimal.valueOf(100.00))
                        .build(),

                Order.builder()
                        .orderID("ijkl-mnop")
                        .status(SUBMITTED)
                        .totalPrice(BigDecimal.valueOf(150.00))
                        .build(),

                Order.builder()
                        .orderID("qrst-uvwx")
                        .status(SUBMITTED)
                        .totalPrice(BigDecimal.valueOf(80.00))
                        .build()
        );

        List<Order> actualOrders = orderRepository.findAll();

        assertThat(actualOrders)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedOrders);
    }

    @Test
    @DisplayName("Should update order status by orderID")
    void shouldUpdateOrderStatusByOrderID() {
        int expectedModifiedOrder = 1;

        int actualModifiedOrder = orderRepository.updateStatusByOrderID(PREPARING, "qrst-uvwx");

        assertThat(actualModifiedOrder)
                .isEqualTo(expectedModifiedOrder);
    }

    @Test
    @DisplayName("Should find order by orderID")
    void findByOrderID() {
        Optional<Order> expectedOrder = Optional.of(
                Order.builder()
                        .orderID("qrst-uvwx")
                        .status(SUBMITTED)
                        .totalPrice(BigDecimal.valueOf(80.00))
                        .build()
        );

        Optional<Order> actualOrder = orderRepository.findByOrderID("qrst-uvwx");

        assertThat(actualOrder.isPresent())
                .isTrue();

        assertThat(actualOrder.get())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedOrder.get());
    }
}