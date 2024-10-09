package project.io.app.core.customer.domain;

import lombok.Getter;
import project.io.app.core.common.BaseEntity;

import java.time.LocalDateTime;

@Getter
public class Customer extends BaseEntity {

    private final Long id;
    private final String name;

    public Customer(
        final Long id,
        final String name,
        final LocalDateTime date
    ) {
        validate(id, name);
        this.id = id;
        this.name = name;
        initOperationData(id, date);
    }

    private void validate(
        final Long id,
        final String name
    ) {
        if (id == null) {
            throw new IllegalArgumentException("고객 ID를 입력해주세요.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("고객 이름을 입력해주세요.");
        }
    }
}
