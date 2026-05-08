package com.vault.core.application.UseCase;

import java.util.List;

import com.vault.core.domain.model.Report;

public interface GetPendingReportsUseCase {
    List<Report> getPending();

}
