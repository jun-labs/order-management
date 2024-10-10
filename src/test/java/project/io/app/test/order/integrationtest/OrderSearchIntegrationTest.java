package project.io.app.test.order.integrationtest;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.io.app.common.cursor.Cursor;
import project.io.app.core.order.domain.Order;
import static project.io.app.core.order.domain.OrderStatus.PROCESSING;
import static project.io.app.core.order.exception.OrderCodeAndMessage.ORDER_NOT_FOUND;
import project.io.app.core.order.exception.OrderNotFoundException;
import project.io.app.core.order.service.OrderReadService;
import project.io.app.core.order.service.OrderWriteService;
import project.io.app.test.common.IntegrationTestBase;

import java.math.BigDecimal;
import java.util.List;

@DisplayName("[IntegrationTest] 주문 조회 통합 테스트")
class OrderSearchIntegrationTest extends IntegrationTestBase {

    @Autowired
    private OrderWriteService orderWriteService;

    @Autowired
    private OrderReadService orderReadService;

    @Test
    @DisplayName("존재하지 않는 주문을 조회할 때, OrderNotFoundException이 발생한다.")
    void whenOrderNotFoundThenThrowOrderNotFoundException() {
        final Long invalidOrderId = Long.MAX_VALUE;
        assertThatThrownBy(() -> orderReadService.findById(invalidOrderId))
            .isInstanceOf(OrderNotFoundException.class)
            .hasMessage(ORDER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("주문이 존재하면 ID로 조회할 수 있다.")
    void whenOrderExistsThenCanFindById() {
        final Order newOrder = new Order(1L, "상품명", BigDecimal.valueOf(10000), "고객명", PROCESSING, now());
        final Long orderId = orderWriteService.save(newOrder);
        assertNotNull(orderReadService.findById(orderId));
    }

    @Test
    @DisplayName("Cursor를 사용해 페이징 처리된 주문 목록을 조회할 수 있다.")
    void whenCursorUsedThenCanFindPagedOrders() {
        createTestOrders();

        final Cursor cursor = Cursor.createCursor(null, 10);
        final List<Order> orders = orderReadService.findOrders(cursor);

        assertThat(orders).isNotNull();
        assertThat(orders.size()).isEqualTo(10);
        assertThat(orders.get(0).getName()).isEqualTo("상품명10");
    }

    @Test
    @DisplayName("Cursor를 사용해 특정 인덱스 이전의 주문 목록을 조회할 수 있다.")
    void whenIndexProvidedThenFindPreviousOrders() {
        createTestOrders();

        final Cursor cursor = Cursor.createCursor(5L, 10);
        final List<Order> orders = orderReadService.findOrders(cursor);

        assertThat(orders).isNotNull();
        assertThat(orders.size()).isEqualTo(4);
    }

    private void createTestOrders() {
        for (long index = 1; index <= 10; index++) {
            orderWriteService.save(
                new Order(index, "상품명" + index, BigDecimal.valueOf(10000 * index), "고객명" + index, PROCESSING, now())
            );
        }
    }
}
