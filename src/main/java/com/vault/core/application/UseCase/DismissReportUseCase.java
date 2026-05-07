package com.vault.core.application.UseCase;

import java.util.UUID;

public interface DismissReportUseCase {
    void dismiss(UUID reportId, UUID moderatorId, String reason);

}
