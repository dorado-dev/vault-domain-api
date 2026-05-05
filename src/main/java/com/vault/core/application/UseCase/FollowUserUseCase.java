package com.vault.core.application.UseCase;

import java.util.UUID;

public interface FollowUserUseCase {
    void follow(UUID followerId, UUID followedId);

}
