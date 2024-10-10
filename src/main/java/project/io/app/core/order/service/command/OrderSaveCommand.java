package project.io.app.core.order.service.command;

import project.io.app.core.order.domain.Order;
import project.io.app.core.order.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderSaveCommand(
    Long orderId,
    String name,
    String customerName,
    BigDecimal totalPrice,
    OrderStatus orderStatus,
    LocalDateTime orderDate
) {
    public Order toEntity() {
        return new Order(
            orderId,
            name,
            totalPrice,
            customerName,
            orderStatus,
            orderDate
        );
    }
}
