package com.vault.core.application.UseCase;

import java.util.UUID;

import com.vault.core.domain.model.Report;
import com.vault.core.domain.model.enums.ReportReason;

public interface ReportContentUseCase {
    Report report(UUID reporterId, UUID postId, ReportReason reason, String details);

}
