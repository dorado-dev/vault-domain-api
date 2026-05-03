package com.vault.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vault.core.domain.exception.InvalidDomainDataException;

public class Follow extends BaseDomainEntity {
    private UUID followerId;
    private UUID followingId;

    private Follow(UUID id, UUID followerId, UUID followingId) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        this.followerId = followerId;
        this.followingId = followingId;
    }

    public static Follow create(UUID followerId, UUID followingId) {
        if (followerId == null || followingId == null) {
            throw new InvalidDomainDataException("Follower ID and Following ID cannot be null.");
        }

        if (followerId.equals(followingId)) {
            throw new InvalidDomainDataException("A user cannot follow themselves.");
        }

        return new Follow(
                UUID.randomUUID(),
                followerId,
                followingId);
    }

    public UUID getFollowerId() {
        return followerId;
    }

    public UUID getFollowingId() {
        return followingId;
    }

}
