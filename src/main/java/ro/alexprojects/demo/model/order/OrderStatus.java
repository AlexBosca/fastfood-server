package ro.alexprojects.demo.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    SUBMITTED("SUBMITTED"),
    PREPARING("PREPARING"),
    DONE("DONE"),
    PAID("PAID");

    private final String value;
}
