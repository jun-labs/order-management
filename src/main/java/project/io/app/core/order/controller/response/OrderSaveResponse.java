package project.io.app.core.order.controller.response;

import lombok.Getter;

@Getter
public class OrderSaveResponse {

    private Long orderId;

    private OrderSaveResponse() {
    }

    public OrderSaveResponse(final Long orderId) {
        this.orderId = orderId;
    }
}
