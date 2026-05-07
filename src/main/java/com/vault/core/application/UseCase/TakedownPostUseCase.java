package com.vault.core.application.UseCase;

import java.util.UUID;

public interface TakedownPostUseCase {
    void takedown(UUID reportId, UUID moderatorId, String reason);

}
