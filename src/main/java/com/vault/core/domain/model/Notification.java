package com.vault.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vault.core.domain.model.enums.NotificationType;

public class Notification extends BaseDomainEntity {
    private UUID recipientId;
    private NotificationType type;
    private String message;
    private UUID referenceId;
    private boolean isRead;

    private Notification(UUID id, UUID recipientId, NotificationType type, String message, UUID referenceId,
            boolean isRead) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        this.recipientId = recipientId;
        this.type = type;
        this.message = message;
        this.referenceId = referenceId;
        this.isRead = isRead;
    }

    public static Notification create(UUID recipientId, NotificationType type, String message, UUID referenceId) {
        if (recipientId == null) {
            throw new IllegalArgumentException("Recipient ID cannot be null.");
        }
        if (type == null) {
            throw new IllegalArgumentException("Notification type cannot be null.");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Notification message cannot be empty.");
        }

        return new Notification(
                UUID.randomUUID(),
                recipientId,
                type,
                message.trim(),
                referenceId,
                false);
    }

    public void markAsRead() {
        if (this.isRead) {
            return;
        }
        this.isRead = true;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getRecipientId() {
        return recipientId;
    }

    public NotificationType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public boolean isRead() {
        return isRead;
    }

}
