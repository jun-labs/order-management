package project.io.app.core.order.domain;

import lombok.Getter;
import project.io.app.core.common.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class OrderDetail extends BaseEntity {

    private final Long id;
    private final String name;
    private final BigDecimal price;
    private final Long productId;
    private final Long orderId;
    private final int quantity;

    public OrderDetail(
        final Long id,
        final String name,
        final BigDecimal price,
        final Long productId,
        final Long orderId,
        final int quantity,
        final Long userId,
        final LocalDateTime date
    ) {
        validate(id, name, price, productId, orderId, quantity);
        this.id = id;
        this.name = name;
        this.price = price;
        this.productId = productId;
        this.orderId = orderId;
        this.quantity = quantity;
        initOperationData(userId, date);
    }

    private void validate(
        final Long id,
        final String name,
        final BigDecimal price,
        final Long productId,
        final Long orderId,
        final int quantity
    ) {
        if (id == null) {
            throw new IllegalArgumentException("ID를 입력해주세요.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("상품명을 입력해주세요.");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("가격은 0보다 커야 합니다.");
        }
        if (productId == null) {
            throw new IllegalArgumentException("상품 ID를 입력해주세요.");
        }
        if (orderId == null) {
            throw new IllegalArgumentException("주문 ID를 입력해주세요.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }
    }
}

