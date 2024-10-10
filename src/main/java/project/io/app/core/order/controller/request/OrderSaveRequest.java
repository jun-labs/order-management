package project.io.app.core.order.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderSaveRequest {

    @NotNull(message = "올바른 주문 ID를 입력해줴요.")
    @Min(value = 1, message = "올바른 주문 ID를 입력해주세요.")
    @Max(value = Long.MAX_VALUE, message = "올바른 주문 ID를 입력해주세요.")
    private Long orderId;

    private OrderSaveRequest() {
    }

    public OrderSaveRequest(final Long orderId) {
        this.orderId = orderId;
    }
}
