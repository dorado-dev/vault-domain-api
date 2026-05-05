package com.vault.core.application.UseCase;

import java.util.List;
import java.util.UUID;

import com.vault.core.domain.model.Notification;

public interface GetUnreadNotificationsUseCase {
    long getUnreadCount(UUID userId);

    List<Notification> getUnread(UUID userId);

}
