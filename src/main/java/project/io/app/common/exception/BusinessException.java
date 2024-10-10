package project.io.app.common.exception;

import lombok.Getter;
import project.io.app.common.codeandmessage.CodeAndMessage;

@Getter
public class BusinessException extends RuntimeException {

    private final CodeAndMessage codeAndMessage;

    public BusinessException(final CodeAndMessage codeAndMessage) {
        super(codeAndMessage.getMessage());
        this.codeAndMessage = codeAndMessage;
    }
}
