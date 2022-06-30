package ro.alexprojects.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.alexprojects.demo.model.order.Order;
import ro.alexprojects.demo.model.order.OrderStatus;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Transactional
    @Modifying
    @Query("update orders o set o.status = ?1 where o.orderID = ?2")
    int updateStatusByOrderID(OrderStatus status, String orderID);
    Optional<Order> findByOrderID(String orderID);
}
