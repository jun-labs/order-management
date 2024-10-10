package project.io.app.core.order.exception;

import lombok.Getter;
import project.io.app.common.codeandmessage.CodeAndMessage;
import project.io.app.common.exception.BusinessException;

@Getter
public class OrderNotFoundException extends BusinessException {

    private final CodeAndMessage codeAndMessage;

    public OrderNotFoundException(final CodeAndMessage codeAndMessage) {
        super(codeAndMessage);
        this.codeAndMessage = codeAndMessage;
    }
}
