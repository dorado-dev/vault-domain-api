package com.vault.core.domain.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.vault.core.domain.exception.IllegalDomainStateException;
import com.vault.core.domain.exception.InvalidDomainDataException;
import com.vault.core.domain.model.enums.ReportReason;
import com.vault.core.domain.model.enums.ReportStatus;

@DisplayName("Tests del Modelo de Dominio: Report")
class ReportTest {
    @Nested
    @DisplayName("Método: create()")
    class CreateReportTests {

        @Test
        @DisplayName("Debe crear un Report correctamente en estado OPEN con detalles válidos")
        void shouldCreateReportWithValidData() {
            UUID reporterId = UUID.randomUUID();
            UUID postId = UUID.randomUUID();
            ReportReason reason = ReportReason.values()[0];
            String details = "   Contenido ofensivo   ";

            Report report = Report.create(reporterId, postId, reason, details);

            assertAll(
                    () -> assertNotNull(report.getId()),
                    () -> assertEquals(reporterId, report.getReporterId()),
                    () -> assertEquals(postId, report.getPostId()),
                    () -> assertEquals(reason, report.getReason()),
                    () -> assertEquals("Contenido ofensivo", report.getDetails()),
                    () -> assertEquals(ReportStatus.OPEN, report.getStatus()),
                    () -> assertNotNull(report.getCreatedAt()));
        }

        @Test
        @DisplayName("Debe inicializar los detalles como string vacío si se pasan como null")
        void shouldInitializeDetailsAsEmptyStringWhenNull() {
            UUID reporterId = UUID.randomUUID();
            UUID postId = UUID.randomUUID();
            ReportReason reason = ReportReason.values()[0];

            Report report = Report.create(reporterId, postId, reason, null);

            assertEquals("", report.getDetails());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el reporterId es nulo")
        void shouldThrowExceptionWhenReporterIdIsNull() {
            UUID postId = UUID.randomUUID();
            ReportReason reason = ReportReason.values()[0];

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Report.create(null, postId, reason, "Detalles"));

            assertEquals("Reporter ID and Post ID cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el postId es nulo")
        void shouldThrowExceptionWhenPostIdIsNull() {
            UUID reporterId = UUID.randomUUID();
            ReportReason reason = ReportReason.values()[0];

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Report.create(reporterId, null, reason, "Detalles"));

            assertEquals("Reporter ID and Post ID cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el reason es nulo")
        void shouldThrowExceptionWhenReasonIsNull() {
            UUID reporterId = UUID.randomUUID();
            UUID postId = UUID.randomUUID();

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Report.create(reporterId, postId, null, "Detalles"));

            assertEquals("A report reason must be provided.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Método: resolve()")
    class ResolveTests {

        @Test
        @DisplayName("Debe cambiar el estado a RESOLVED y actualizar la fecha si estaba OPEN")
        void shouldResolveWhenOpen() throws InterruptedException {
            Report report = Report.create(UUID.randomUUID(), UUID.randomUUID(), ReportReason.values()[0], "Detalles");
            LocalDateTime originalUpdatedAt = report.getUpdatedAt();

            Thread.sleep(10);

            report.resolve();

            assertAll(
                    () -> assertEquals(ReportStatus.RESOLVED, report.getStatus()),
                    () -> assertTrue(report.getUpdatedAt().isAfter(originalUpdatedAt)));
        }

        @Test
        @DisplayName("Debe lanzar IllegalDomainStateException si intentamos resolver un reporte ya procesado")
        void shouldThrowExceptionWhenResolvingNonOpenReport() {
            Report report = Report.create(UUID.randomUUID(), UUID.randomUUID(), ReportReason.values()[0], "Detalles");
            report.resolve();

            IllegalDomainStateException exception = assertThrows(
                    IllegalDomainStateException.class,
                    () -> report.resolve());

            assertTrue(exception.getMessage().startsWith("Only open reports can be processed"));
        }
    }

    @Nested
    @DisplayName("Método: dismiss()")
    class DismissTests {

        @Test
        @DisplayName("Debe cambiar el estado a DISMISSED y actualizar la fecha si estaba OPEN")
        void shouldDismissWhenOpen() throws InterruptedException {
            Report report = Report.create(UUID.randomUUID(), UUID.randomUUID(), ReportReason.values()[0], "Detalles");
            LocalDateTime originalUpdatedAt = report.getUpdatedAt();

            Thread.sleep(10);

            report.dismiss();

            assertAll(
                    () -> assertEquals(ReportStatus.DISMISSED, report.getStatus()),
                    () -> assertTrue(report.getUpdatedAt().isAfter(originalUpdatedAt)));
        }

        @Test
        @DisplayName("Debe lanzar IllegalDomainStateException si intentamos descartar un reporte ya procesado")
        void shouldThrowExceptionWhenDismissingNonOpenReport() {
            Report report = Report.create(UUID.randomUUID(), UUID.randomUUID(), ReportReason.values()[0], "Detalles");
            report.dismiss();

            IllegalDomainStateException exception = assertThrows(
                    IllegalDomainStateException.class,
                    () -> report.dismiss());

            assertTrue(exception.getMessage().startsWith("Only open reports can be processed"));
        }
    }

}
