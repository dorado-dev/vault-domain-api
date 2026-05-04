package com.vault.core.domain.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vault.core.domain.model.ModerationAction;

public interface ModerationActionRepository {
    void save(ModerationAction moderationAction);

    Optional<ModerationAction> findById(UUID id);

    List<ModerationAction> findByTargetId(UUID targetId);

    List<ModerationAction> findByModeratorId(UUID moderatorId);

}
