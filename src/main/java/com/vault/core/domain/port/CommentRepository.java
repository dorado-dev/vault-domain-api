package com.vault.core.domain.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vault.core.domain.model.Comment;

public interface CommentRepository {
    void save(Comment comment);

    Optional<Comment> findById(UUID id);

    List<Comment> findAllByPostId(UUID postId);

    void delete(Comment comment);

    long countByPostId(UUID postId);

}
