package com.vault.core.application.UseCase;

import java.util.UUID;

public interface DeleteOwnPostUseCase {
    void delete(UUID postId, UUID authorId);

}
