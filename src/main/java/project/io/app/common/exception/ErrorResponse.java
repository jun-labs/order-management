package project.io.app.common.exception;

import lombok.Getter;
import project.io.app.common.codeandmessage.CodeAndMessage;

@Getter
public class ErrorResponse {

    private int code;
    private String message;

    private ErrorResponse() {
    }

    public ErrorResponse(final CodeAndMessage codeAndMessage) {
        this.code = codeAndMessage.getCode();
        this.message = codeAndMessage.getMessage();
    }
}
