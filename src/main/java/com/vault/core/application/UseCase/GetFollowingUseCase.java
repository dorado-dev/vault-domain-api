package com.vault.core.application.UseCase;

import java.util.List;
import java.util.UUID;

import com.vault.core.domain.model.UserProfile;

public interface GetFollowingUseCase {
    List<UserProfile> getFollowing(UUID userId);

}
