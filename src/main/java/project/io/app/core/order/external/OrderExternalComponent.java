package project.io.app.core.order.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.INVALID_ARGUMENT;
import project.io.app.common.exception.ExternalDataFetchingFailedException;
import project.io.app.common.exception.InvalidDataFetchingException;
import project.io.app.core.order.external.response.OrderDataResponse;
import project.io.app.core.order.external.validator.OrderDataValidator;
import project.io.app.core.order.service.command.OrderSaveCommand;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderExternalComponent {

    @Value("${external.url}")
    private String url;

    private final OrderDataValidator validator;
    private final RestTemplate restTemplate;

    @Retryable(
        retryFor = {HttpServerErrorException.class, ResourceAccessException.class},
        backoff = @Backoff(delay = 1_000, maxDelay = 3_000, multiplier = 1.5, random = true)
    )
    public OrderSaveCommand fetchOrderData(final Long orderId) {
        try {
            final String url = this.url + orderId;
            final ResponseEntity<OrderDataResponse> response = restTemplate.getForEntity(url, OrderDataResponse.class);
            validator.validate(response);
            final OrderDataResponse payload = response.getBody();
            return createCommand(payload);
        } catch (HttpClientErrorException | IllegalArgumentException ex) {
            log.error("주문 데이터를 받아오는데 실패했습니다. 주문 ID:{}", orderId);
            throw new InvalidDataFetchingException(INVALID_ARGUMENT);
        }
    }

    private static OrderSaveCommand createCommand(final OrderDataResponse payload) {
        return new OrderSaveCommand(
            payload.getOrderId(),
            payload.getName(),
            payload.getCustomerName(),
            payload.getTotalPrice(),
            payload.getOrderStatus(),
            payload.getOrderDate()
        );
    }

    @Recover
    public OrderSaveCommand recover(
        final ExternalDataFetchingFailedException ex,
        final Long orderId
    ) {
        log.error("모든 재시도가 실패했습니다. 주문 ID: {}", orderId, ex);
        throw ex;
    }
}
