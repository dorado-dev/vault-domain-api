package com.vault.core.domain.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.vault.core.domain.exception.InvalidDomainDataException;
import com.vault.core.domain.model.enums.ModerationActionType;
import com.vault.core.domain.model.enums.TargetType;

@DisplayName("Tests del Modelo de Dominio: ModerationAction")
class ModerationActionTest {
    @Nested
    @DisplayName("Método: create()")
    class CreateModerationActionTests {

        @Test
        @DisplayName("Debe crear una acción de moderación correctamente con todos los datos y un reporte vinculado")
        void shouldCreateModerationActionWithAllData() {
            UUID moderatorId = UUID.randomUUID();
            UUID targetId = UUID.randomUUID();
            TargetType targetType = TargetType.values()[0];
            ModerationActionType actionType = ModerationActionType.values()[0];
            String reason = "   Incumplimiento de normas   ";
            UUID reportId = UUID.randomUUID();

            ModerationAction action = ModerationAction.create(moderatorId, targetId, targetType, actionType, reason,
                    reportId);

            assertAll(
                    () -> assertNotNull(action.getId()),
                    () -> assertEquals(moderatorId, action.getModeratorId()),
                    () -> assertEquals(targetId, action.getTargetId()),
                    () -> assertEquals(targetType, action.getTargetType()),
                    () -> assertEquals(actionType, action.getActionType()),
                    () -> assertEquals("Incumplimiento de normas", action.getReason()),
                    () -> assertEquals(reportId, action.getReportId()),
                    () -> assertNotNull(action.getCreatedAt()));
        }

        @Test
        @DisplayName("Debe crear una acción de moderación proactiva correctamente (reportId es nulo)")
        void shouldCreateProactiveModerationActionWhenReportIdIsNull() {
            UUID moderatorId = UUID.randomUUID();
            UUID targetId = UUID.randomUUID();
            TargetType targetType = TargetType.values()[0];
            ModerationActionType actionType = ModerationActionType.values()[0];
            String reason = "Moderación proactiva del staff";

            ModerationAction action = ModerationAction.create(moderatorId, targetId, targetType, actionType, reason,
                    null);

            assertNull(action.getReportId());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el moderatorId es nulo")
        void shouldThrowExceptionWhenModeratorIdIsNull() {
            TargetType targetType = TargetType.values()[0];
            ModerationActionType actionType = ModerationActionType.values()[0];

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> ModerationAction.create(null, UUID.randomUUID(), targetType, actionType, "Razón", null));

            assertEquals("Moderator and target are mandatory for auditing purposes.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el targetId es nulo")
        void shouldThrowExceptionWhenTargetIdIsNull() {
            TargetType targetType = TargetType.values()[0];
            ModerationActionType actionType = ModerationActionType.values()[0];

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> ModerationAction.create(UUID.randomUUID(), null, targetType, actionType, "Razón", null));

            assertEquals("Moderator and target are mandatory for auditing purposes.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el targetType es nulo")
        void shouldThrowExceptionWhenTargetTypeIsNull() {
            ModerationActionType actionType = ModerationActionType.values()[0];

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> ModerationAction.create(UUID.randomUUID(), UUID.randomUUID(), null, actionType, "Razón",
                            null));

            assertEquals("Target type and action type cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el actionType es nulo")
        void shouldThrowExceptionWhenActionTypeIsNull() {
            TargetType targetType = TargetType.values()[0];

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> ModerationAction.create(UUID.randomUUID(), UUID.randomUUID(), targetType, null, "Razón",
                            null));

            assertEquals("Target type and action type cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si la razón está vacía")
        void shouldThrowExceptionWhenReasonIsEmpty() {
            TargetType targetType = TargetType.values()[0];
            ModerationActionType actionType = ModerationActionType.values()[0];

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> ModerationAction.create(UUID.randomUUID(), UUID.randomUUID(), targetType, actionType, "   ",
                            null));

            assertEquals("A reason must be provided for the moderation action.", exception.getMessage());
        }
    }

}
