package project.io.app.common.exception;

import lombok.Getter;
import project.io.app.common.codeandmessage.CodeAndMessage;

@Getter
public class InvalidDataFetchingException extends RuntimeException {

    private final CodeAndMessage codeAndMessage;

    public InvalidDataFetchingException(final CodeAndMessage codeAndMessage) {
        super(codeAndMessage.getMessage());
        this.codeAndMessage = codeAndMessage;
    }
}
