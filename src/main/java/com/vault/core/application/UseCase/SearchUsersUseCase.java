package com.vault.core.application.UseCase;

import java.util.List;

import com.vault.core.domain.model.UserProfile;

public interface SearchUsersUseCase {
    List<UserProfile> search(String query);

}
