package project.io.app.test.customer.unittest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.io.app.core.customer.domain.Customer;

import java.time.LocalDateTime;

@DisplayName("[UnitTest] 고객 단위 테스트")
class CustomerUnitTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        final LocalDateTime now = LocalDateTime.now();
        customer = new Customer(
            1L,
            "John",
            now
        );
    }

    @Test
    @DisplayName("올바른 인자가 들어오면 Customer가 생성된다.")
    void whenValidCustomerThenCustomerShouldBeCreated() {
        final LocalDateTime now = LocalDateTime.now();
        final Customer newCustomer = new Customer(
            1L,
            "John",
            now
        );

        assertAll(
            () -> assertThat(newCustomer.getId()).isEqualTo(1L),
            () -> assertThat(newCustomer.getName()).isEqualTo("John"),
            () -> assertThat(newCustomer.getCreatedAt()).isEqualTo(now),
            () -> assertThat(newCustomer.getCreatedBy()).isEqualTo(1L),
            () -> assertThat(newCustomer.getLastModifiedAt()).isEqualTo(now),
            () -> assertThat(newCustomer.getLastModifiedBy()).isEqualTo(1L),
            () -> assertThat(newCustomer.isDeleted()).isFalse()
        );
    }

    @Test
    @DisplayName("고객 ID가 null이면 IllegalArgumentException이 발생한다.")
    void whenCustomerIdIsNullThenIllegalArgumentExceptionShouldBeThrown() {
        final LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> new Customer(
            null,
            "John Doe",
            now
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("고객 ID를 입력해주세요.");
    }

    @Test
    @DisplayName("고객 이름이 비어있으면 IllegalArgumentException이 발생한다.")
    void whenCustomerNameIsBlankThenIllegalArgumentExceptionShouldBeThrown() {
        final LocalDateTime now = LocalDateTime.now();

        assertAll(
            () -> assertThatThrownBy(() -> new Customer(
                1L,
                " ",
                now
            ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객 이름을 입력해주세요."),

            () -> assertThatThrownBy(() -> new Customer(
                1L,
                null,
                now
            ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객 이름을 입력해주세요.")
        );
    }
}
