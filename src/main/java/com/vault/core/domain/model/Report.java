package com.vault.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vault.core.domain.model.enums.ReportReason;
import com.vault.core.domain.model.enums.ReportStatus;

public class Report extends BaseDomainEntity {
    private UUID reporterId;
    private UUID postId;
    private ReportReason reason;
    private String details;
    private ReportStatus status;

    private Report(UUID id, UUID reporterId, UUID postId, ReportReason reason, String details, ReportStatus status) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        this.reporterId = reporterId;
        this.postId = postId;
        this.reason = reason;
        this.details = details;
        this.status = status;
    }

    public static Report create(UUID reporterId, UUID postId, ReportReason reason, String details) {
        if (reporterId == null || postId == null) {
            throw new IllegalArgumentException("Reporter ID and Post ID cannot be null.");
        }
        if (reason == null) {
            throw new IllegalArgumentException("A report reason must be provided.");
        }

        return new Report(
                UUID.randomUUID(),
                reporterId,
                postId,
                reason,
                details != null ? details.trim() : "",
                ReportStatus.OPEN);
    }

    public void resolve() {
        ensureIsPending();
        this.status = ReportStatus.RESOLVED;
        this.updatedAt = LocalDateTime.now();
    }

    public void dismiss() {
        ensureIsPending();
        this.status = ReportStatus.DISMISSED;
        this.updatedAt = LocalDateTime.now();
    }

    private void ensureIsPending() {
        if (this.status != ReportStatus.OPEN) {
            throw new IllegalStateException("Only open reports can be processed. Current status: " + this.status);
        }
    }

    public UUID getReporterId() {
        return reporterId;
    }

    public UUID getPostId() {
        return postId;
    }

    public ReportReason getReason() {
        return reason;
    }

    public String getDetails() {
        return details;
    }

    public ReportStatus getStatus() {
        return status;
    }

}
