package com.vault.core.application.UseCase;

import java.util.UUID;

public interface UnfollowUserUseCase {
    void unfollow(UUID followerId, UUID followedId);

}
