package project.io.app.test.order.unittest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.BAD_GATEWAY;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.EMPTY_DATA;
import static project.io.app.common.codeandmessage.CommonCodeAndMessage.INVALID_ARGUMENT;
import project.io.app.common.exception.InvalidDataFetchingException;
import project.io.app.core.order.domain.OrderStatus;
import project.io.app.core.order.external.response.OrderDataResponse;
import project.io.app.core.order.external.validator.OrderDataValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DisplayName("[UnitTest] OrderValidator 단위 테스트")
class OrderDataValidatorUnitTest {

    private OrderDataValidator validator;

    @BeforeEach
    void setUp() {
        validator = new OrderDataValidator();
    }

    @Test
    @DisplayName("response가 null일 때 InvalidDataFetchingException이 발생한다.")
    void whenResponseIsNull_thenInvalidDataFetchingExceptionShouldBeThrown() {
        assertThatThrownBy(() -> validator.validate(null))
            .isInstanceOf(InvalidDataFetchingException.class)
            .hasMessage(BAD_GATEWAY.getMessage());
    }

    @Test
    @DisplayName("payload가 null일 때 InvalidDataFetchingException이 발생한다.")
    void whenPayloadIsNull_thenInvalidDataFetchingExceptionShouldBeThrown() {
        final ResponseEntity<OrderDataResponse> response = new ResponseEntity<>(null, HttpStatus.OK);

        assertThatThrownBy(() -> validator.validate(response))
            .isInstanceOf(InvalidDataFetchingException.class)
            .hasMessage(EMPTY_DATA.getMessage());
    }

    @Test
    @DisplayName("주문 ID가 null일 때 InvalidDataFetchingException이 발생한다.")
    void whenOrderIdIsNull_thenInvalidDataFetchingExceptionShouldBeThrown() {
        OrderDataResponse payload = new OrderDataResponse(
            null, "상품명", "고객명", BigDecimal.TEN, LocalDateTime.now(), OrderStatus.PROCESSING);
        ResponseEntity<OrderDataResponse> response = new ResponseEntity<>(payload, HttpStatus.OK);

        assertThatThrownBy(() -> validator.validate(response))
            .isInstanceOf(InvalidDataFetchingException.class)
            .hasMessage(INVALID_ARGUMENT.getMessage());
    }

    @Test
    @DisplayName("고객명이 null이거나 빈 문자열일 때 InvalidDataFetchingException이 발생한다.")
    void whenCustomerNameIsNullOrBlank_thenInvalidDataFetchingExceptionShouldBeThrown() {
        final OrderDataResponse payload = new OrderDataResponse(
            1L, "상품명", null, BigDecimal.TEN, LocalDateTime.now(), OrderStatus.PROCESSING
        );
        final ResponseEntity<OrderDataResponse> response = new ResponseEntity<>(payload, HttpStatus.OK);

        assertThatThrownBy(() -> validator.validate(response))
            .isInstanceOf(InvalidDataFetchingException.class)
            .hasMessage(INVALID_ARGUMENT.getMessage());

        final OrderDataResponse blankNamePayload = new OrderDataResponse(
            1L, "상품명", "   ", BigDecimal.TEN, LocalDateTime.now(), OrderStatus.PROCESSING
        );
        final ResponseEntity<OrderDataResponse> blankNameResponse = new ResponseEntity<>(blankNamePayload, HttpStatus.OK);

        assertThatThrownBy(() -> validator.validate(blankNameResponse))
            .isInstanceOf(InvalidDataFetchingException.class)
            .hasMessage(INVALID_ARGUMENT.getMessage());
    }

    @Test
    @DisplayName("주문 상태가 null일 때 InvalidDataFetchingException이 발생한다.")
    void whenOrderStatusIsNull_thenInvalidDataFetchingExceptionShouldBeThrown() {
        final OrderDataResponse payload = new OrderDataResponse(
            1L, "상품명", "고객명", BigDecimal.TEN, LocalDateTime.now(), null
        );
        final ResponseEntity<OrderDataResponse> response = new ResponseEntity<>(payload, HttpStatus.OK);

        assertThatThrownBy(() -> validator.validate(response))
            .isInstanceOf(InvalidDataFetchingException.class)
            .hasMessage(INVALID_ARGUMENT.getMessage());
    }

    @Test
    @DisplayName("주문 날짜가 null일 때 InvalidDataFetchingException이 발생한다.")
    void whenOrderDateIsNull_thenInvalidDataFetchingExceptionShouldBeThrown() {
        final OrderDataResponse payload = new OrderDataResponse(
            1L, "상품명", "고객명", BigDecimal.TEN, null, OrderStatus.PROCESSING
        );
        final ResponseEntity<OrderDataResponse> response = new ResponseEntity<>(payload, HttpStatus.OK);

        assertThatThrownBy(() -> validator.validate(response))
            .isInstanceOf(InvalidDataFetchingException.class)
            .hasMessage(INVALID_ARGUMENT.getMessage());
    }
}
