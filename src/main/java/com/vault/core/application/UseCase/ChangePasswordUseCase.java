package com.vault.core.application.UseCase;

import java.util.UUID;

public interface ChangePasswordUseCase {
    void changePassword(UUID userId, String currentPassword, String newPassword);

}
