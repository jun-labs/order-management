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
    private final Long userId;
    private final LocalDateTime orderDate;

    public Order(
        final Long id,
        final String name,
        final BigDecimal totalPrice,
        final Long userId,
        final LocalDateTime orderDate
    ) {
        validate(id, name, totalPrice, userId, orderDate);
        this.id = id;
        this.name = name;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.orderDate = orderDate;
        initOperationData(userId, orderDate);
    }

    private void validate(
        final Long id,
        final String name,
        final BigDecimal totalPrice,
        final Long userId,
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
        if (userId == null) {
            throw new IllegalArgumentException("올바른 고객 ID를 입력해주세요.");
        }
        if (orderDate == null) {
            throw new IllegalArgumentException("주문 날짜를 입력해주세요.");
        }
    }
}
