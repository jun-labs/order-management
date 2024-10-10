package project.io.app.core.order.domain;

import lombok.Getter;
import project.io.app.core.common.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class Order extends BaseEntity {

    private final Long id;
    private final String name;
    private final BigDecimal totalPrice;
    private final String customerName;
    private final OrderStatus orderStatus;
    private final LocalDateTime orderDate;

    public Order(
        final Long id,
        final String name,
        final BigDecimal totalPrice,
        final String customerName,
        final OrderStatus orderStatus,
        final LocalDateTime orderDate
    ) {
        validate(id, name, totalPrice, customerName, orderStatus, orderDate);
        this.id = id;
        this.name = name;
        this.totalPrice = totalPrice;
        this.customerName = customerName;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        initOperationData(null, orderDate);
    }

    private void validate(
        final Long id,
        final String name,
        final BigDecimal totalPrice,
        final String customerName,
        final OrderStatus orderStatus,
        final LocalDateTime orderDate
    ) {
        if (id == null) {
            throw new IllegalArgumentException("ID를 입력해주세요.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("올바른 주문명을 입력해주세요.");
        }
        if (totalPrice == null || totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("총 가격은 0보다 커야 합니다.");
        }
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("올바른 고객명을 입력해주세요.");
        }
        if (orderStatus == null) {
            throw new IllegalArgumentException("올바른 주문 상태를 입력해주세요.");
        }
        if (orderDate == null) {
            throw new IllegalArgumentException("주문 날짜를 입력해주세요.");
        }
    }

    public String getOrderDateAsString() {
        return orderDate.toString();
    }
}
