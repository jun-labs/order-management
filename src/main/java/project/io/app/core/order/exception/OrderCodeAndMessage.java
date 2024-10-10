package project.io.app.core.order.exception;

import project.io.app.common.codeandmessage.CodeAndMessage;

public enum OrderCodeAndMessage implements CodeAndMessage {
    ORDER_NOT_FOUND(404, "주문을 찾을 수 없습니다.");

    private final int code;
    private final String message;

    OrderCodeAndMessage(
        final int code,
        final String message
    ) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
