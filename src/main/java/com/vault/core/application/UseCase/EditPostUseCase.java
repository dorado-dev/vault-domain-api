package com.vault.core.application.UseCase;

import java.util.UUID;

import com.vault.core.domain.model.Post;

public interface EditPostUseCase {
    Post edit(UUID postId, UUID authorId, String newContent, String newMediaUrl);

}
