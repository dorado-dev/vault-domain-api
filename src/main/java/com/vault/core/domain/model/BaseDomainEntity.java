package com.vault.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseDomainEntity {
    protected UUID id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BaseDomainEntity that = (BaseDomainEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

}
