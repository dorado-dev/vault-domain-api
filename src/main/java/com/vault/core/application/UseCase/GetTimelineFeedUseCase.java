package com.vault.core.application.UseCase;

import java.util.List;
import java.util.UUID;

import com.vault.core.domain.model.Post;

public interface GetTimelineFeedUseCase {
    List<Post> getFeed(UUID userId);

}
