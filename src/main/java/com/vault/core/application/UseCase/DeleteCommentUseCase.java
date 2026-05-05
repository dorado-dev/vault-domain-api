package com.vault.core.application.UseCase;

import java.util.UUID;

public interface DeleteCommentUseCase {
    void delete(UUID userId, UUID commentId);

}
