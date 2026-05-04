package com.vault.core.domain.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vault.core.domain.model.UserProfile;

public interface UserProfileRepository {
    void save(UserProfile userProfile);

    Optional<UserProfile> findById(UUID id);

    Optional<UserProfile> findByUserId(UUID userId);

    Optional<UserProfile> findByUsername(String username);

    boolean existsByUsername(String username);

    List<UserProfile> searchByNameOrUsername(String query);

}
