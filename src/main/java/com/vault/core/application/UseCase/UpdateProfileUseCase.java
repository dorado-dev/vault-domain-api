package com.vault.core.application.UseCase;

import java.util.UUID;

import com.vault.core.domain.model.UserProfile;

public interface UpdateProfileUseCase {
    UserProfile update(UUID userId, String fullName, String bio, String avatarUrl);

}
