package com.vault.core.application.UseCase;

import java.util.UUID;

public interface DeleteAccountUseCase {
    void deleteAccount(UUID userId, String confirmationPassword);

}
