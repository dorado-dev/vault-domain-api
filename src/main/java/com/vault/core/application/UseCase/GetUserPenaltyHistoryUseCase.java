package com.vault.core.application.UseCase;

import java.util.List;
import java.util.UUID;

import com.vault.core.domain.model.ModerationAction;

public interface GetUserPenaltyHistoryUseCase {
    List<ModerationAction> getHistory(UUID userId);

}
