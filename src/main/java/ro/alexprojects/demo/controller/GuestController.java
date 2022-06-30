package ro.alexprojects.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.alexprojects.demo.dto.meal.MealResponse;
import ro.alexprojects.demo.dto.order.OrderRequest;
import ro.alexprojects.demo.dto.order.OrderResponse;
import ro.alexprojects.demo.mappers.MapStructMapper;
import ro.alexprojects.demo.service.GuestService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/guest")
@AllArgsConstructor
public class GuestController {
    private final GuestService guestService;
    private final MapStructMapper mapStructMapper;

    @GetMapping(path = "/menu")
    public ResponseEntity<List<MealResponse>> getMenu() {
        return new ResponseEntity<>(
                mapStructMapper.mealsToMealResponses(guestService.getMenu()),
                OK
        );
    }

    @PostMapping(path = "/order")
    public ResponseEntity<Void> placeOrder(@RequestBody OrderRequest orderRequest) {
        guestService.placeOrder(
                mapStructMapper.orderRequestToOrder(orderRequest)
        );

        return new ResponseEntity<>(CREATED);
    }

    @PutMapping(path = "/pay/{orderID}")
    public ResponseEntity<Void> payOrder(@PathVariable(name = "orderID") String orderID) {
        guestService.payOrder(orderID);

        return new ResponseEntity<>(OK);
    }

    @GetMapping(path = "/order/{orderID}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable(name = "orderID") String orderID) {
        return new ResponseEntity<>(
                mapStructMapper.orderToOrderResponse(guestService.getOrder(orderID)),
                OK
        );
    }
}
