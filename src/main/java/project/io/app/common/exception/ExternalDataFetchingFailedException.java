package project.io.app.common.exception;

import lombok.Getter;
import project.io.app.common.codeandmessage.CodeAndMessage;

@Getter
public class ExternalDataFetchingFailedException extends RuntimeException {

    private final CodeAndMessage codeAndMessage;

    public ExternalDataFetchingFailedException(final CodeAndMessage codeAndMessage) {
        super(codeAndMessage.getMessage());
        this.codeAndMessage = codeAndMessage;
    }
}
