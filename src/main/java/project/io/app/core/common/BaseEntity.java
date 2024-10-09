package project.io.app.core.common;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntity {
    protected LocalDateTime createdAt;
    protected LocalDateTime lastModifiedAt;
    protected Long createdBy;
    protected Long lastModifiedBy;
    protected boolean deleted;

    protected void initOperationData(
        final Long createdBy,
        final LocalDateTime now
    ) {
        this.createdAt = now;
        this.lastModifiedAt = now;
        this.createdBy = createdBy;
        this.lastModifiedBy = createdBy;
        this.deleted = false;
    }
}
