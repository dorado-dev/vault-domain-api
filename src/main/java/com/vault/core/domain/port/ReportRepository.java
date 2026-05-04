package com.vault.core.domain.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vault.core.domain.model.Report;
import com.vault.core.domain.model.enums.ReportStatus;

public interface ReportRepository {
    void save(Report report);

    Optional<Report> findById(UUID id);

    List<Report> findByStatus(ReportStatus status);

    List<Report> findByPostId(UUID postId);

}
