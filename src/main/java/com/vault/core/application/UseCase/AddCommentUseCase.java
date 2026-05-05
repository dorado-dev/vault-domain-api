package com.vault.core.application.UseCase;

import java.util.UUID;

import com.vault.core.domain.model.Comment;

public interface AddCommentUseCase {
    Comment add(UUID authorId, UUID postId, String content);

}
