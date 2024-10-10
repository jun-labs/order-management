package project.io.app.core.order.external.validator;

import org.springframework.stereotype.Component;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.EMPTY_DATA;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.INVALID_ARGUMENT;
import project.io.app.common.exception.InvalidDataFetchingException;
import project.io.app.core.order.external.response.OrderDataResponse;

@Component
public class OrderDataValidator {

    public void validate(final OrderDataResponse payload) {
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
