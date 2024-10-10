package project.io.app.core.order.controller.response;

import lombok.Getter;
import project.io.app.core.order.domain.Order;
import project.io.app.core.order.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class OrderDetailResponse {

    private Long orderId;
    private String name;
    private BigDecimal totalPrice;
    private String customerName;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;

    private OrderDetailResponse() {
    }

    public OrderDetailResponse(final Order order) {
        this.orderId = order.getId();
        this.name = order.getName();
        this.totalPrice = order.getTotalPrice();
        this.customerName = order.getCustomerName();
        this.orderStatus = order.getOrderStatus();
        this.orderDate = order.getOrderDate();
    }
}
