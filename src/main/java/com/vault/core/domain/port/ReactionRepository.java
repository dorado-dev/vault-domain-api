package com.vault.core.domain.port;

import java.util.Optional;
import java.util.UUID;

import com.vault.core.domain.model.Reaction;

public interface ReactionRepository {
    void save(Reaction reaction);

    Optional<Reaction> findByPostIdAndUserId(UUID postId, UUID userId);

    void delete(Reaction reaction);

    int countByPostId(UUID postId);

}
