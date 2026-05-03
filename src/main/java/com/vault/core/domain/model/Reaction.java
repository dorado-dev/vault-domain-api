package com.vault.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vault.core.domain.exception.InvalidDomainDataException;
import com.vault.core.domain.model.enums.ReactionType;

public class Reaction extends BaseDomainEntity {
    private UUID postId;
    private UUID userId;
    private ReactionType type;

    private Reaction(UUID id, UUID userId, UUID postId, ReactionType type) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        this.userId = userId;
        this.postId = postId;
        this.type = type;
    }

    public static Reaction create(UUID userId, UUID postId, ReactionType type) {
        if (userId == null || postId == null) {
            throw new InvalidDomainDataException("User ID and Post ID cannot be null.");
        }
        if (type == null) {
            throw new InvalidDomainDataException("A reaction type must be provided.");
        }

        return new Reaction(
                UUID.randomUUID(),
                userId,
                postId,
                type);
    }

    public void changeType(ReactionType newType) {
        if (newType == null) {
            throw new InvalidDomainDataException("The new reaction type cannot be null.");
        }
        if (this.type == newType) {
            throw new InvalidDomainDataException("The reaction is already of this type.");
        }

        this.type = newType;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getPostId() {
        return postId;
    }

    public ReactionType getType() {
        return type;
    }

}
