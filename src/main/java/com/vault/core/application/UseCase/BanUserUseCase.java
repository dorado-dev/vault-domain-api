package com.vault.core.application.UseCase;

import java.time.LocalDateTime;
import java.util.UUID;

public interface BanUserUseCase {
    void ban(UUID targetUserId, UUID moderatorId, String reason, LocalDateTime expiresAt);

}
