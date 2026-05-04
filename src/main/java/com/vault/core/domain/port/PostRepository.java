package com.vault.core.domain.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vault.core.domain.model.Post;
import com.vault.core.domain.model.enums.PostStatus;

public interface PostRepository {
    void save(Post post);

    Optional<Post> findById(UUID id);

    List<Post> findByAuthorId(UUID authorId);

    List<Post> findByAuthorIdsAndStatus(List<UUID> authorIds, PostStatus status);

    void delete(Post post);

}
