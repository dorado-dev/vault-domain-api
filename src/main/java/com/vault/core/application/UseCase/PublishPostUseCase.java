package com.vault.core.application.UseCase;

import java.util.UUID;

import com.vault.core.domain.model.Post;

public interface PublishPostUseCase {
    Post publish(UUID authorId, String content, String mediaUrl);

}
