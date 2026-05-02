package com.vault.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vault.core.domain.model.enums.ModerationActionType;
import com.vault.core.domain.model.enums.TargetType;

public class ModerationAction extends BaseDomainEntity {
    private UUID moderatorId;
    private UUID targetId;
    private UUID reportId;
    private TargetType targetType;
    private ModerationActionType actionType;
    private String reason;

    private ModerationAction(UUID id, UUID moderatorId, UUID targetId, TargetType targetType,
            ModerationActionType actionType, String reason, UUID reportId) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        this.moderatorId = moderatorId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.actionType = actionType;
        this.reason = reason;
        this.reportId = reportId;
    }

    public static ModerationAction create(UUID moderatorId, UUID targetId, TargetType targetType,
            ModerationActionType actionType, String reason, UUID reportId) {

        if (moderatorId == null || targetId == null) {
            throw new IllegalArgumentException("Moderator and target are mandatory for auditing purposes.");
        }
        if (targetType == null || actionType == null) {
            throw new IllegalArgumentException("Target type and action type cannot be null.");
        }
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("A reason must be provided for the moderation action.");
        }

        return new ModerationAction(
                UUID.randomUUID(),
                moderatorId,
                targetId,
                targetType,
                actionType,
                reason,
                reportId);
    }

    public UUID getModeratorId() {
        return moderatorId;
    }

    public UUID getTargetId() {
        return targetId;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public ModerationActionType getActionType() {
        return actionType;
    }

    public String getReason() {
        return reason;
    }

    public UUID getReportId() {
        return reportId;
    }

}
