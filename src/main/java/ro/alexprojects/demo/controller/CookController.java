package ro.alexprojects.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.alexprojects.demo.dto.order.OrderResponse;
import ro.alexprojects.demo.mappers.MapStructMapper;
import ro.alexprojects.demo.service.CookService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/cook")
@AllArgsConstructor
public class CookController {
    private final CookService cookService;
    private final MapStructMapper mapStructMapper;

    @PutMapping(path = "/prepare/{orderID}")
    public ResponseEntity<Void> prepareOrder(@PathVariable(name = "orderID") String orderID) {
        cookService.prepareOrder(orderID);

        return new ResponseEntity<>(OK);
    }

    @PutMapping(path = "/finish/{orderID}")
    public ResponseEntity<Void> finishOrder(@PathVariable(name = "orderID") String orderID) {
        cookService.finishOrder(orderID);

        return new ResponseEntity<>(OK);
    }

    @GetMapping(path = "/orders")
    public ResponseEntity<List<OrderResponse>> getOrders() {
        return new ResponseEntity<>(
                mapStructMapper.ordersToOrderResponses(cookService.getOrders()),
                OK
        );
    }
}
