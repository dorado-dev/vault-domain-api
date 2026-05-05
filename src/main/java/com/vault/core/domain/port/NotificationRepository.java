package com.vault.core.domain.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vault.core.domain.model.Notification;

public interface NotificationRepository {
    void save(Notification notification);

    Optional<Notification> findById(UUID id);

    List<Notification> findAllByRecipientId(UUID recipientId);

    long countUnreadByRecipientId(UUID recipientId);

    List<Notification> findUnreadByRecipientId(UUID recipientId);

}
