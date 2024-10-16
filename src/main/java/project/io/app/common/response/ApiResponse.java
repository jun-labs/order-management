package project.io.app.common.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.io.app.common.codeandmessage.CodeAndMessage;

public class ApiResponse<T> extends ResponseEntity<Payload<T>> {

    public ApiResponse(
        final CodeAndMessage codeAndMessage,
        final HttpHeaders headers,
        final T data
    ) {
        super(new Payload<>(codeAndMessage, data), headers, HttpStatus.valueOf(codeAndMessage.getCode()));
    }

    public static <T> ApiResponse<T> from(final CodeAndMessage codeAndMessage) {
        return new ApiResponse<>(
            codeAndMessage,
            null,
            null
        );
    }

    public static <T> ApiResponse<T> from(
        final CodeAndMessage codeAndMessage,
        final T data
    ) {
        return new ApiResponse<>(
            codeAndMessage,
            null,
            data
        );
    }

    public static <T> ApiResponse<T> from(
        final CodeAndMessage codeAndMessage,
        final HttpHeaders headers,
        final T data
    ) {
        return new ApiResponse<>(
            codeAndMessage,
            headers,
            data
        );
    }

    @Override
    public String toString() {
        final String payloadStr = (getBody() != null) ? getBody().toString() : null;
        final String headerStr = getHeaders().toString();
        return "{\"status\":"
            + getStatusCodeValue()
            + ", \"headers\":"
            + headerStr
            + ", \"body\":"
            + payloadStr
            + "}";
    }
}
