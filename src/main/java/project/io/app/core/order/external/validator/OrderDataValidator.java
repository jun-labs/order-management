package project.io.app.core.order.external.validator;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.BAD_GATEWAY;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.EMPTY_DATA;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.INVALID_ARGUMENT;
import project.io.app.common.exception.InvalidDataFetchingException;
import project.io.app.core.order.external.response.OrderDataResponse;

@Component
public class OrderDataValidator {

    public void validate(final ResponseEntity<OrderDataResponse> response) {
        if (response == null) {
            throw new InvalidDataFetchingException(BAD_GATEWAY);
        }
        final OrderDataResponse payload = response.getBody();
        if (payload == null) {
            throw new InvalidDataFetchingException(EMPTY_DATA);
        }

        if (payload.getOrderId() == null) {
            throw new InvalidDataFetchingException(INVALID_ARGUMENT);
        }
        if (payload.getCustomerName() == null || payload.getCustomerName().isBlank()) {
            throw new InvalidDataFetchingException(INVALID_ARGUMENT);
        }
        if (payload.getOrderStatus() == null) {
            throw new InvalidDataFetchingException(INVALID_ARGUMENT);
        }
        if (payload.getOrderDate() == null) {
            throw new InvalidDataFetchingException(INVALID_ARGUMENT);
        }
    }
}
