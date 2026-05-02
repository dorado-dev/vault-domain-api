package com.vault.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment extends BaseDomainEntity {
    private UUID postId;
    private UUID authorId;
    private String content;

    private Comment(UUID id, UUID authorId, UUID postId, String content) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        this.authorId = authorId;
        this.postId = postId;
        this.content = content;
    }

    public static Comment create(UUID authorId, UUID postId, String content) {
        if (authorId == null || postId == null) {
            throw new IllegalArgumentException("Author ID and Post ID cannot be null.");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be empty.");
        }
        if (content.length() > 300) {
            throw new IllegalArgumentException("Comment exceeds maximum allowed length of 300 characters.");
        }

        return new Comment(
                UUID.randomUUID(),
                authorId,
                postId,
                content.trim());
    }

    public void editContent(String newContent) {
        if (newContent == null || newContent.trim().isEmpty()) {
            throw new IllegalArgumentException("New content cannot be empty.");
        }
        if (newContent.length() > 300) {
            throw new IllegalArgumentException("New content exceeds maximum allowed length of 300 characters.");
        }

        String sanitizedContent = newContent.trim();
        if (this.content.equals(sanitizedContent)) {
            return;
        }

        this.content = sanitizedContent;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public UUID getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

}
