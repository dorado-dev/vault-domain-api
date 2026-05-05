package com.vault.core.application.UseCase;

import java.util.UUID;

import com.vault.core.domain.model.UserProfile;

public interface GetProfileUseCase {
    UserProfile getByUsername(String username);

    UserProfile getByUserId(UUID userId);

}
