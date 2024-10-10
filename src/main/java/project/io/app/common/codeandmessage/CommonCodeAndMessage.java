package project.io.app.common.codeandmessage;

public enum CommonCodeAndMessage implements CodeAndMessage {
    OK(200, "OK"),
    CREATED(201, "Created"),
    INVALID_ARGUMENT(400, "올바른 파라미터를 입력해주세요."),
    BAD_REQUEST(400, "올바른 파라미터를 입력해주세요"),
    INVALID_DATA(400, "데이터가 올바른 양식이 아닙니다."),
    EMPTY_DATA(400, "외부 데이터를 받아오는데 실패했습니다."),
    INVALID_URL(404, "올바르지 않은 URL 입니다."),
    INTERNAL_SERVER(500, "서버 내부 오류입니다."),
    BAD_GATEWAY(502, "외부 서버와 통신하는 과정에서 에러가 발생했습니다.");

    private final int code;
    private final String message;

    CommonCodeAndMessage(
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
