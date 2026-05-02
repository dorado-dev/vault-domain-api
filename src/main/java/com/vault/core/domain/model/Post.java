package com.vault.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vault.core.domain.model.enums.PostStatus;

public class Post extends BaseDomainEntity {
    private UUID authorId;
    private String content;
    private String mediaUrl;
    private PostStatus status;

    private Post(UUID id, UUID authorId, String content, String mediaUrl, PostStatus status) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        this.authorId = authorId;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.status = status;
    }

    public static Post create(UUID authorId, String content, String mediaUrl) {
        if (authorId == null) {
            throw new IllegalArgumentException("Author ID cannot be null.");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Post content cannot be empty.");
        }
        if (content.length() > 500) {
            throw new IllegalArgumentException("Post content exceeds maximum allowed length of 500 characters.");
        }

        return new Post(
                UUID.randomUUID(),
                authorId,
                content.trim(),
                mediaUrl != null && !mediaUrl.trim().isEmpty() ? mediaUrl.trim() : null,
                PostStatus.PUBLISHED);
    }

    public void editContent(String newContent, String newMediaUrl) {
        if (newContent == null || newContent.trim().isEmpty()) {
            throw new IllegalArgumentException("New content cannot be empty.");
        }
        if (newContent.length() > 500) {
            throw new IllegalArgumentException("New content exceeds maximum allowed length of 500 characters.");
        }

        String sanitizedContent = newContent.trim();
        String sanitizedMediaUrl = newMediaUrl != null && !newMediaUrl.trim().isEmpty() ? newMediaUrl.trim() : null;

        boolean contentChanged = !this.content.equals(sanitizedContent);
        boolean mediaChanged = (this.mediaUrl == null && sanitizedMediaUrl != null) ||
                (this.mediaUrl != null && !this.mediaUrl.equals(sanitizedMediaUrl));

        if (!contentChanged && !mediaChanged) {
            return;
        }

        this.content = sanitizedContent;
        this.mediaUrl = sanitizedMediaUrl;
        this.updatedAt = LocalDateTime.now();
    }

    public void hide() {
        if (this.status == PostStatus.HIDDEN) {
            throw new IllegalStateException("The post is already hidden.");
        }
        this.status = PostStatus.HIDDEN;
        this.updatedAt = LocalDateTime.now();
    }

    public void makePublic() {
        if (this.status == PostStatus.PUBLISHED) {
            throw new IllegalStateException("The post is already published.");
        }
        this.status = PostStatus.PUBLISHED;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public String getContent() {
        return content;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public PostStatus getStatus() {
        return status;
    }

}
