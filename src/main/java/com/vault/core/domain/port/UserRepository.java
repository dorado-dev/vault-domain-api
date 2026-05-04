package com.vault.core.domain.port;

import java.util.Optional;
import java.util.UUID;

import com.vault.core.domain.model.User;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    void delete(User user);

}
