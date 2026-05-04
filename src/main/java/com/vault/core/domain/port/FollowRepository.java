package com.vault.core.domain.port;

import java.util.Optional;
import java.util.UUID;

import com.vault.core.domain.model.Follow;

public interface FollowRepository {
    void save(Follow follow);

    Optional<Follow> findByFollowerIdAndFollowingId(UUID followerId, UUID followingId);

    void delete(Follow follow);

    int countByFollowingId(UUID followingId);

    int countByFollowerId(UUID followerId);

}
