package com.vault.core.application.UseCase;

import java.util.UUID;

public interface MarkNotificationAsReadUseCase {
    void markAsRead(UUID notificationId, UUID userId);

    void markAllAsRead(UUID userId);

}
