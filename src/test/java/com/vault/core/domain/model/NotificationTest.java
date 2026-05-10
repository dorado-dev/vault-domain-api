package com.vault.core.domain.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.vault.core.domain.exception.InvalidDomainDataException;
import com.vault.core.domain.model.enums.NotificationType;

@DisplayName("Tests del Modelo de Dominio: Notification")
class NotificationTest {
    @Nested
    @DisplayName("Método: create()")
    class CreateNotificationTests {

        @Test
        @DisplayName("Debe crear una Notification correctamente como no leída")
        void shouldCreateNotificationWithValidData() {
            UUID recipientId = UUID.randomUUID();
            NotificationType type = NotificationType.values()[0];
            String message = "   Tienes un nuevo seguidor   ";
            UUID referenceId = UUID.randomUUID();

            Notification notification = Notification.create(recipientId, type, message, referenceId);

            assertAll(
                    () -> assertNotNull(notification.getId()),
                    () -> assertEquals(recipientId, notification.getRecipientId()),
                    () -> assertEquals(type, notification.getType()),
                    () -> assertEquals("Tienes un nuevo seguidor", notification.getMessage()),
                    () -> assertEquals(referenceId, notification.getReferenceId()),
                    () -> assertFalse(notification.isRead()),
                    () -> assertNotNull(notification.getCreatedAt()));
        }

        @Test
        @DisplayName("Debe crear una Notification correctamente aunque referenceId sea nulo")
        void shouldCreateNotificationWhenReferenceIdIsNull() {
            UUID recipientId = UUID.randomUUID();
            NotificationType type = NotificationType.values()[0];
            String message = "Notificación global del sistema";

            Notification notification = Notification.create(recipientId, type, message, null);

            assertNull(notification.getReferenceId());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el recipientId es nulo")
        void shouldThrowExceptionWhenRecipientIdIsNull() {
            NotificationType type = NotificationType.values()[0];

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Notification.create(null, type, "Mensaje", UUID.randomUUID()));

            assertEquals("Recipient ID cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el type es nulo")
        void shouldThrowExceptionWhenTypeIsNull() {
            UUID recipientId = UUID.randomUUID();

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Notification.create(recipientId, null, "Mensaje", UUID.randomUUID()));

            assertEquals("Notification type cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el message está vacío")
        void shouldThrowExceptionWhenMessageIsEmpty() {
            UUID recipientId = UUID.randomUUID();
            NotificationType type = NotificationType.values()[0];

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Notification.create(recipientId, type, "   ", UUID.randomUUID()));

            assertEquals("Notification message cannot be empty.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Método: markAsRead()")
    class MarkAsReadTests {

        @Test
        @DisplayName("Debe marcar como leída y actualizar la fecha si estaba sin leer")
        void shouldMarkAsReadAndUpdateDateWhenUnread() throws InterruptedException {
            Notification notification = Notification.create(
                    UUID.randomUUID(), NotificationType.values()[0], "Mensaje", null);
            LocalDateTime originalUpdatedAt = notification.getUpdatedAt();

            Thread.sleep(10);

            notification.markAsRead();

            assertAll(
                    () -> assertTrue(notification.isRead()),
                    () -> assertTrue(notification.getUpdatedAt().isAfter(originalUpdatedAt)));
        }

        @Test
        @DisplayName("No debe actualizar nada si la notificación ya estaba leída")
        void shouldNotUpdateDateWhenAlreadyRead() throws InterruptedException {
            Notification notification = Notification.create(
                    UUID.randomUUID(), NotificationType.values()[0], "Mensaje", null);
            notification.markAsRead();
            LocalDateTime timeAfterFirstRead = notification.getUpdatedAt();

            Thread.sleep(10);

            notification.markAsRead();

            assertAll(
                    () -> assertTrue(notification.isRead()),
                    () -> assertEquals(timeAfterFirstRead, notification.getUpdatedAt()));
        }
    }

}
