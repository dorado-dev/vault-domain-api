package com.vault.core.application.UseCase;

import java.util.List;
import java.util.UUID;

import com.vault.core.domain.model.Comment;

public interface GetPostCommentsUseCase {
    List<Comment> getByPostId(UUID postId);

}
