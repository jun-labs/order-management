package project.io.app.common.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import project.io.app.common.codeandmessage.CodeAndMessage;
import project.io.app.common.codeandmessage.CommonCodeAndMessage;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.BAD_REQUEST;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.INTERNAL_SERVER;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.INVALID_ARGUMENT;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.INVALID_DATA;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({JsonMappingException.class, JsonParseException.class, HttpMessageConversionException.class})
    public ResponseEntity<ErrorResponse> resolveParsingException(
        final MissingServletRequestParameterException ex
    ) {
        log.error("message:{}", ex.getMessage());
        final CodeAndMessage codeAndMessage = INVALID_DATA;
        return ResponseEntity.status(codeAndMessage.getCode())
            .body(new ErrorResponse(codeAndMessage));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> resolveBusinessException(final BusinessException ex) {
        log.error("message:{}", ex.getMessage());
        final CodeAndMessage codeAndMessage = ex.getCodeAndMessage();
        return ResponseEntity.status(codeAndMessage.getCode())
            .body(new ErrorResponse(codeAndMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> resolveMethodArgumentNotValidException(
        final MethodArgumentNotValidException ex
    ) {
        log.error("message:{}", ex.getMessage());
        final CodeAndMessage codeAndMessage = INVALID_DATA;
        return ResponseEntity.status(codeAndMessage.getCode())
            .body(new ErrorResponse(codeAndMessage));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> resolveHttpMessageNotReadableException(
        final MissingServletRequestParameterException ex
    ) {
        log.error("message:{}", ex.getMessage());
        final CodeAndMessage codeAndMessage = INVALID_DATA;
        return ResponseEntity.status(codeAndMessage.getCode())
            .body(new ErrorResponse(codeAndMessage));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> resolveMissingServletRequestParameterException(
        final MissingServletRequestParameterException ex
    ) {
        log.error("message:{}", ex.getMessage());
        final CodeAndMessage codeAndMessage = BAD_REQUEST;
        return ResponseEntity.status(codeAndMessage.getCode())
            .body(new ErrorResponse(codeAndMessage));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> resolveMethodArgumentTypeMismatchException(
        final MethodArgumentTypeMismatchException ex
    ) {
        log.error("message:{}", ex.getMessage());
        final CodeAndMessage codeAndMessage = BAD_REQUEST;
        return ResponseEntity.status(codeAndMessage.getCode())
            .body(new ErrorResponse(codeAndMessage));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> resolveNoHandlerFoundException(final NoHandlerFoundException ex) {
        log.error("message:{}", ex.getMessage());
        final CommonCodeAndMessage codeAndMessage = CommonCodeAndMessage.INVALID_URL;
        return ResponseEntity.status(codeAndMessage.getCode())
            .body(new ErrorResponse(codeAndMessage));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> resolveIllegalArgumentException(final IllegalArgumentException ex) {
        log.error("message:{}", ex.getMessage());
        final CodeAndMessage codeAndMessage = INVALID_ARGUMENT;
        return ResponseEntity.status(codeAndMessage.getCode())
            .body(new ErrorResponse(codeAndMessage));
    }

    @ExceptionHandler({InvalidDataFetchingException.class, ExternalDataFetchingFailedException.class})
    public ResponseEntity<ErrorResponse> resolveInvalidDataFetchingException(final InvalidDataFetchingException ex) {
        log.error("message:{}", ex.getMessage());
        final CodeAndMessage codeAndMessage = ex.getCodeAndMessage();
        return ResponseEntity.status(codeAndMessage.getCode())
            .body(new ErrorResponse(codeAndMessage));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> resolveIllegalStateException(final IllegalStateException ex) {
        log.error("message:{}", ex.getMessage());
        final CodeAndMessage codeAndMessage = INTERNAL_SERVER;
        return ResponseEntity.status(codeAndMessage.getCode())
            .body(new ErrorResponse(codeAndMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> resolveException(final Exception ex) {
        log.error("message:{}", ex.getMessage());
        final CodeAndMessage codeAndMessage = INTERNAL_SERVER;
        return ResponseEntity.status(codeAndMessage.getCode())
            .body(new ErrorResponse(codeAndMessage));
    }
}
