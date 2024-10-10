package project.io.app.test.order.unittest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.ExhaustedRetryException;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import project.io.app.core.order.domain.OrderStatus;
import project.io.app.core.order.external.OrderExternalComponent;
import project.io.app.core.order.external.response.OrderDataResponse;
import project.io.app.core.order.service.command.OrderSaveCommand;
import project.io.app.test.common.IntegrationTestBase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EnableRetry
@DisplayName("[UnitTest] OrderExternalComponent 단위 테스트")
class OrderExternalComponentUnitTest extends IntegrationTestBase {

    @Autowired
    private OrderExternalComponent component;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    @DisplayName("올바른 파라미터를 넣으면 데이터를 받아올 수 있다.")
    void whenFetchDataWithValidParameterThenResultShouldNotBeNull() {
        final OrderDataResponse expectedResponse = new OrderDataResponse(
            1L, "Item Name", "Customer Name", BigDecimal.valueOf(100_00), LocalDateTime.now(), OrderStatus.PROCESSING);
        final ResponseEntity<OrderDataResponse> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(eq("https://external.api/orders/1"), eq(OrderDataResponse.class)))
            .thenReturn(responseEntity);

        final OrderSaveCommand result = component.fetchOrderData(1L);
        assertNotNull(result);
    }

    @Test
    @DisplayName("HTTP 요청이 실패하고 HttpServerErrorException이 발생하면, Retry가 3번 시도된다.")
    void whenHttpRequestFailedWithHttpServerErrorExceptionThenRetryShouldBeExecuted() {
        when(restTemplate.getForEntity(eq("https://external.api/orders/10000"), eq(OrderDataResponse.class)))
            .thenThrow(new HttpServerErrorException(INTERNAL_SERVER_ERROR));

        assertThrows(ExhaustedRetryException.class, () -> component.fetchOrderData(10000L));
        verify(restTemplate, times(3)).getForEntity(eq("https://external.api/orders/10000"), eq(OrderDataResponse.class));
    }
}
