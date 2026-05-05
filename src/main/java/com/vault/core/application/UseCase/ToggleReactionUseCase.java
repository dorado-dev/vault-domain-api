package com.vault.core.application.UseCase;

import java.util.UUID;

import com.vault.core.domain.model.enums.ReactionType;

public interface ToggleReactionUseCase {
    void toggle(UUID userId, UUID postId, ReactionType reactionType);

}
