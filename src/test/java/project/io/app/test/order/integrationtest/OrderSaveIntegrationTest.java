package project.io.app.test.order.integrationtest;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import static project.io.app.core.order.domain.OrderStatus.PROCESSING;
import project.io.app.core.order.external.OrderExternalComponent;
import project.io.app.core.order.external.response.OrderDataResponse;
import project.io.app.core.order.facade.OrderFacade;
import project.io.app.core.order.service.command.OrderSaveCommand;
import project.io.app.test.common.IntegrationTestBase;

import java.math.BigDecimal;

@DisplayName("[IntegrationTest] 주문 저장 통합 테스트")
class OrderSaveIntegrationTest extends IntegrationTestBase {

    @MockBean
    private OrderExternalComponent component;

    @Autowired
    private OrderFacade orderFacade;

    @Test
    @DisplayName("외부 데이터를 가져와 주문 저장 성공 시, 반환된 주문 ID는 null이 아니어야 한다.")
    void whenFetchDataThenOrderIdShouldNotBeNull() {
        final Long orderId = 1L;
        final OrderDataResponse response = new OrderDataResponse(
            orderId, "상품명", "고객명", BigDecimal.valueOf(100_000), now(), PROCESSING
        );
        final OrderSaveCommand command = new OrderSaveCommand(
            response.getOrderId(),
            response.getName(),
            response.getCustomerName(),
            response.getTotalPrice(),
            response.getOrderStatus(),
            response.getOrderDate()
        );

        when(component.fetchOrderData(eq(orderId)))
            .thenReturn(command);

        final Long savedOrderId = orderFacade.save(orderId);

        verify(component).fetchOrderData(eq(orderId));

        assertThat(savedOrderId).isNotNull();
    }
}
