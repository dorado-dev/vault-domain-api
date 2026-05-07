package com.vault.core.application.UseCase;

import com.vault.core.domain.model.UserProfile;

public interface RegisterUserUseCase {
    UserProfile register(String email, String username, String fullName, String rawPassword);

}
