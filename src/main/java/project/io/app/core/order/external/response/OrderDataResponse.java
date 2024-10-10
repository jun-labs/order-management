package project.io.app.core.order.external.response;

import lombok.Getter;
import project.io.app.core.order.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class OrderDataResponse {

    private Long orderId;
    private String name;
    private String customerName;
    private BigDecimal totalPrice;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;

    private OrderDataResponse() {
    }

    public OrderDataResponse(
        final Long orderId,
        final String name,
        final String customerName,
        final BigDecimal totalPrice,
        final LocalDateTime orderDate,
        final OrderStatus orderStatus
    ) {
        this.orderId = orderId;
        this.name = name;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }
}
