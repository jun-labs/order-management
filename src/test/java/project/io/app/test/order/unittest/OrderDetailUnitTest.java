package project.io.app.test.order.unittest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.io.app.core.order.domain.OrderDetail;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DisplayName("[UnitTest] 주문 상세 단위 테스트")
class OrderDetailUnitTest {

    private OrderDetail orderDetail;

    @BeforeEach
    void setUp() {
        final LocalDateTime now = LocalDateTime.now();
        orderDetail = new OrderDetail(
            1L,
            "마케팅 결제",
            new BigDecimal("100000.00"),
            101L,
            1001L,
            2,
            1L,
            now
        );
    }

    @Test
    @DisplayName("올바른 인자가 들어오면 OrderDetail이 생성된다.")
    void whenValidOrderDetailThenOrderDetailShouldBeCreated() {
        final LocalDateTime now = LocalDateTime.now();
        final OrderDetail newOrderDetail = new OrderDetail(
            1L,
            "Product A",
            new BigDecimal("100000.00"),
            101L,
            1001L,
            2,
            1L,
            now
        );

        assertAll(
            () -> assertThat(newOrderDetail.getId()).isEqualTo(1L),
            () -> assertThat(newOrderDetail.getName()).isEqualTo("Product A"),
            () -> assertThat(newOrderDetail.getPrice()).isEqualTo(new BigDecimal("100000.00")),
            () -> assertThat(newOrderDetail.getProductId()).isEqualTo(101L),
            () -> assertThat(newOrderDetail.getOrderId()).isEqualTo(1001L),
            () -> assertThat(newOrderDetail.getQuantity()).isEqualTo(2),
            () -> assertThat(newOrderDetail.getCreatedAt()).isEqualTo(now),
            () -> assertThat(newOrderDetail.getCreatedBy()).isEqualTo(1L),
            () -> assertThat(newOrderDetail.getLastModifiedAt()).isEqualTo(now),
            () -> assertThat(newOrderDetail.getLastModifiedBy()).isEqualTo(1L),
            () -> assertThat(newOrderDetail.isDeleted()).isFalse()
        );
    }

    @Test
    @DisplayName("ID가 null이면 IllegalArgumentException이 발생한다.")
    void whenIdIsNullThenIllegalArgumentExceptionShouldBeThrown() {
        final LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> new OrderDetail(
            null,
            "Product A",
            new BigDecimal("100000.00"),
            101L,
            1001L,
            2,
            1L,
            now
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("ID를 입력해주세요.");
    }

    @Test
    @DisplayName("이름이 비어 있으면 IllegalArgumentException이 발생한다.")
    void whenNameIsBlankThenIllegalArgumentExceptionShouldBeThrown() {
        final LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> new OrderDetail(
            1L,
            "  ",
            new BigDecimal("100000.00"),
            101L,
            1001L,
            2,
            1L,
            now
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("상품명을 입력해주세요.");
    }

    @Test
    @DisplayName("가격이 null이면 IllegalArgumentException이 발생한다.")
    void whenPriceIsNullThenIllegalArgumentExceptionShouldBeThrown() {
        final LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> new OrderDetail(
            1L,
            "Product A",
            null,
            101L,
            1001L,
            2,
            1L,
            now
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("가격은 0보다 커야 합니다.");
    }

    @Test
    @DisplayName("가격이 0 이하이면 IllegalArgumentException이 발생한다.")
    void whenPriceIsZeroOrLessThenIllegalArgumentExceptionShouldBeThrown() {
        final LocalDateTime now = LocalDateTime.now();

        assertAll(
            () -> assertThatThrownBy(() -> new OrderDetail(
                1L,
                "Product A",
                BigDecimal.ZERO,
                101L,
                1001L,
                2,
                1L,
                now
            ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 0보다 커야 합니다."),

            () -> assertThatThrownBy(() -> new OrderDetail(
                1L,
                "Product A",
                new BigDecimal("-100"),
                101L,
                1001L,
                2,
                1L,
                now
            ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 0보다 커야 합니다.")
        );
    }

    @Test
    @DisplayName("상품 ID가 null이면 IllegalArgumentException이 발생한다.")
    void whenProductIdIsNullThenIllegalArgumentExceptionShouldBeThrown() {
        final LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> new OrderDetail(
            1L,
            "Product A",
            new BigDecimal("100000.00"),
            null,
            1001L,
            2,
            1L,
            now
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("상품 ID를 입력해주세요.");
    }

    @Test
    @DisplayName("주문 ID가 null이면 IllegalArgumentException이 발생한다.")
    void whenOrderIdIsNullThenIllegalArgumentExceptionShouldBeThrown() {
        final LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> new OrderDetail(
            1L,
            "Product A",
            new BigDecimal("100000.00"),
            101L,
            null,
            2,
            1L,
            now
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문 ID를 입력해주세요.");
    }

    @Test
    @DisplayName("수량이 0 이하이면 IllegalArgumentException이 발생한다.")
    void whenQuantityIsZeroOrLessThenIllegalArgumentExceptionShouldBeThrown() {
        final LocalDateTime now = LocalDateTime.now();

        assertAll(
            () -> assertThatThrownBy(() -> new OrderDetail(
                1L,
                "Product A",
                new BigDecimal("100000.00"),
                101L,
                1001L,
                0,
                1L,
                now
            ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수량은 1 이상이어야 합니다."),

            () -> assertThatThrownBy(() -> new OrderDetail(
                1L,
                "Product A",
                new BigDecimal("100000.00"),
                101L,
                1001L,
                -1,
                1L,
                now
            ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수량은 1 이상이어야 합니다.")
        );
    }
}
