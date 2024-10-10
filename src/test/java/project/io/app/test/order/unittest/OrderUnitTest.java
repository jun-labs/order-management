package project.io.app.test.order.unittest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.io.app.core.order.domain.Order;
import project.io.app.core.order.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DisplayName("[UnitTest] 주문 단위 테스트")
class OrderUnitTest {

    private Order order;

    @BeforeEach
    void setUp() {
        final LocalDateTime now = LocalDateTime.now();
        order = new Order(
            1L,
            "마케팅 결제 외 2건",
            new BigDecimal("100000.00"),
            "고객명",
            OrderStatus.PROCESSING,
            now
        );
    }

    @Test
    @DisplayName("올바른 인자가 들어오면 주문이 생성된다.")
    void whenValidOrderThenOrderShouldBeCreated() {
        final LocalDateTime now = LocalDateTime.now();
        final Order newOrder = new Order(
            1L,
            "마케팅 결제 외 2건",
            new BigDecimal("100000.00"),
            "고객명",
            OrderStatus.PROCESSING,
            now
        );

        assertAll(
            () -> assertThat(newOrder.getId()).isEqualTo(1L),
            () -> assertThat(newOrder.getName()).isEqualTo("마케팅 결제 외 2건"),
            () -> assertThat(newOrder.getTotalPrice()).isEqualTo(new BigDecimal("100000.00")),
            () -> assertThat(newOrder.getCustomerName()).isEqualTo("고객명"),
            () -> assertThat(newOrder.getOrderStatus()).isEqualTo(OrderStatus.PROCESSING),
            () -> assertThat(newOrder.getOrderDate()).isEqualTo(now),
            () -> assertThat(newOrder.getCreatedAt()).isEqualTo(now),
            () -> assertThat(newOrder.getLastModifiedAt()).isEqualTo(now),
            () -> assertThat(newOrder.isDeleted()).isFalse()
        );
    }

    @Test
    @DisplayName("ID가 null이면 IllegalArgumentException이 발생한다.")
    void whenIdIsNullThenIllegalArgumentExceptionShouldBeThrown() {
        final LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> new Order(
            null,
            "마케팅 결제 외 2건",
            new BigDecimal("100000.00"),
            "고객명",
            OrderStatus.PROCESSING,
            now
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("ID를 입력해주세요.");
    }

    @Test
    @DisplayName("이름이 비어 있으면 IllegalArgumentException이 발생한다.")
    void whenNameIsBlankThenIllegalArgumentExceptionShouldBeThrown() {
        final LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> new Order(
            1L,
            "  ",
            new BigDecimal("100000.00"),
            "고객명",
            OrderStatus.PROCESSING,
            now
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("올바른 주문명을 입력해주세요.");
    }

    @Test
    @DisplayName("총 가격이 null이면 IllegalArgumentException이 발생한다.")
    void whenTotalPriceIsNullThenIllegalArgumentExceptionShouldBeThrown() {
        final LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> new Order(
            1L,
            "마케팅 결제 외 2건",
            null,
            "고객명",
            OrderStatus.PROCESSING,
            now
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("총 가격은 0보다 커야 합니다.");
    }

    @Test
    @DisplayName("총 가격이 0보다 작거나 같으면 IllegalArgumentException이 발생한다.")
    void whenTotalPriceIsLessThanOrEqualToZeroThenIllegalArgumentExceptionShouldBeThrown() {
        final LocalDateTime now = LocalDateTime.now();

        assertAll(
            () -> assertThatThrownBy(() -> new Order(
                1L,
                "마케팅 결제 외 2건",
                BigDecimal.ZERO,
                "고객명",
                OrderStatus.PROCESSING,
                now
            ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("총 가격은 0보다 커야 합니다."),

            () -> assertThatThrownBy(() -> new Order(
                1L,
                "마케팅 결제 외 2건",
                new BigDecimal("-100"),
                "고객명",
                OrderStatus.PROCESSING,
                now
            ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("총 가격은 0보다 커야 합니다.")
        );
    }

    @Test
    @DisplayName("고객명이 null이면 IllegalArgumentException이 발생한다.")
    void whenCustomerNameIsNullThenIllegalArgumentExceptionShouldBeThrown() {
        final LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> new Order(
            1L,
            "마케팅 결제 외 2건",
            new BigDecimal("100000.00"),
            null,
            OrderStatus.PROCESSING,
            now
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("올바른 고객명을 입력해주세요.");
    }

    @Test
    @DisplayName("주문 날짜가 null이면 IllegalArgumentException이 발생한다.")
    void whenOrderDateIsNullThenIllegalArgumentExceptionShouldBeThrown() {
        assertThatThrownBy(() -> new Order(
            1L,
            "마케팅 결제 외 2건",
            new BigDecimal("100000.00"),
            "고객명",
            OrderStatus.PROCESSING,
            null
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문 날짜를 입력해주세요.");
    }
}
