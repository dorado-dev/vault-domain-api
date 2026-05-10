package com.vault.core.domain.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.vault.core.domain.exception.IllegalDomainStateException;
import com.vault.core.domain.exception.InvalidDomainDataException;
import com.vault.core.domain.model.enums.RoleType;
import com.vault.core.domain.model.enums.UserStatus;

@DisplayName("Tests del Modelo de Dominio: User")
class UserTest {
    @Nested
    @DisplayName("Método: create()")
    class CreateUserTests {

        @Test
        @DisplayName("Debe crear un User correctamente, normalizando el email y asignando defaults")
        void shouldCreateUserWithValidData() {
            String email = "   John.Doe@EXAMPLE.com  ";
            String passwordHash = "hashed_password_123";

            User user = User.create(email, passwordHash);

            assertAll(
                    () -> assertNotNull(user.getId()),
                    () -> assertEquals("john.doe@example.com", user.getEmail()),
                    () -> assertEquals(passwordHash, user.getPassword()),
                    () -> assertEquals(RoleType.USER, user.getRole()),
                    () -> assertEquals(UserStatus.ACTIVE, user.getStatus()),
                    () -> assertNotNull(user.getCreatedAt()));
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el email es nulo")
        void shouldThrowExceptionWhenEmailIsNull() {
            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> User.create(null, "password_hash"));

            assertEquals("A valid email is required.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el email no contiene arroba")
        void shouldThrowExceptionWhenEmailIsInvalid() {
            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> User.create("johndoe.com", "password_hash"));

            assertEquals("A valid email is required.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el password está vacío")
        void shouldThrowExceptionWhenPasswordIsEmpty() {
            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> User.create("test@test.com", "   "));

            assertEquals("Password cannot be empty.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Método: updatePassword()")
    class UpdatePasswordTests {

        @Test
        @DisplayName("Debe actualizar el password y la fecha si los datos son válidos y diferentes")
        void shouldUpdatePasswordAndDate() throws InterruptedException {
            User user = User.create("test@test.com", "old_password");
            LocalDateTime originalUpdatedAt = user.getUpdatedAt();

            Thread.sleep(10);

            user.updatePassword("new_password");

            assertAll(
                    () -> assertEquals("new_password", user.getPassword()),
                    () -> assertTrue(user.getUpdatedAt().isAfter(originalUpdatedAt)));
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el nuevo password está vacío")
        void shouldThrowExceptionWhenNewPasswordIsEmpty() {
            User user = User.create("test@test.com", "old_password");

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> user.updatePassword("   "));

            assertEquals("New password cannot be empty.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el nuevo password es idéntico al actual")
        void shouldThrowExceptionWhenNewPasswordIsIdentical() {
            User user = User.create("test@test.com", "my_password");

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> user.updatePassword("my_password"));

            assertEquals("New password must be different from the current one.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Método: suspend()")
    class SuspendTests {

        @Test
        @DisplayName("Debe cambiar el estado a SUSPENDED y actualizar la fecha si estaba ACTIVE")
        void shouldSuspendWhenActive() throws InterruptedException {
            User user = User.create("test@test.com", "password");
            LocalDateTime originalUpdatedAt = user.getUpdatedAt();

            Thread.sleep(10);

            user.suspend();

            assertAll(
                    () -> assertEquals(UserStatus.SUSPENDED, user.getStatus()),
                    () -> assertTrue(user.getUpdatedAt().isAfter(originalUpdatedAt)));
        }

        @Test
        @DisplayName("No debe actualizar nada si el usuario ya estaba SUSPENDED")
        void shouldNotUpdateDateWhenAlreadySuspended() throws InterruptedException {
            User user = User.create("test@test.com", "password");
            user.suspend();
            LocalDateTime timeAfterSuspend = user.getUpdatedAt();

            Thread.sleep(10);

            user.suspend();

            assertAll(
                    () -> assertEquals(UserStatus.SUSPENDED, user.getStatus()),
                    () -> assertEquals(timeAfterSuspend, user.getUpdatedAt()));
        }

        @Test
        @DisplayName("Debe lanzar IllegalDomainStateException si intentamos suspender un usuario DELETED")
        void shouldThrowExceptionWhenSuspendingDeletedUser() {
            User user = User.create("test@test.com", "password");
            user.deleteLogically();

            IllegalDomainStateException exception = assertThrows(
                    IllegalDomainStateException.class,
                    () -> user.suspend());

            assertEquals("Cannot suspend a deleted user.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Método: activate()")
    class ActivateTests {

        @Test
        @DisplayName("Debe cambiar el estado a ACTIVE y actualizar la fecha si estaba SUSPENDED")
        void shouldActivateWhenSuspended() throws InterruptedException {
            User user = User.create("test@test.com", "password");
            user.suspend();
            LocalDateTime suspendedUpdatedAt = user.getUpdatedAt();

            Thread.sleep(10);

            user.activate();

            assertAll(
                    () -> assertEquals(UserStatus.ACTIVE, user.getStatus()),
                    () -> assertTrue(user.getUpdatedAt().isAfter(suspendedUpdatedAt)));
        }

        @Test
        @DisplayName("No debe actualizar nada si el usuario ya estaba ACTIVE")
        void shouldNotUpdateDateWhenAlreadyActive() throws InterruptedException {
            User user = User.create("test@test.com", "password");
            LocalDateTime originalUpdatedAt = user.getUpdatedAt();

            Thread.sleep(10);

            user.activate();

            assertAll(
                    () -> assertEquals(UserStatus.ACTIVE, user.getStatus()),
                    () -> assertEquals(originalUpdatedAt, user.getUpdatedAt()));
        }

        @Test
        @DisplayName("Debe lanzar IllegalDomainStateException si intentamos activar un usuario DELETED")
        void shouldThrowExceptionWhenActivatingDeletedUser() {
            User user = User.create("test@test.com", "password");
            user.deleteLogically();

            IllegalDomainStateException exception = assertThrows(
                    IllegalDomainStateException.class,
                    () -> user.activate());

            assertEquals("Cannot activate a deleted user.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Método: deleteLogically()")
    class DeleteLogicallyTests {

        @Test
        @DisplayName("Debe cambiar el estado a DELETED y actualizar la fecha")
        void shouldDeleteLogically() throws InterruptedException {
            User user = User.create("test@test.com", "password");
            LocalDateTime originalUpdatedAt = user.getUpdatedAt();

            Thread.sleep(10);

            user.deleteLogically();

            assertAll(
                    () -> assertEquals(UserStatus.DELETED, user.getStatus()),
                    () -> assertTrue(user.getUpdatedAt().isAfter(originalUpdatedAt)));
        }

        @Test
        @DisplayName("No debe actualizar nada si el usuario ya estaba DELETED")
        void shouldNotUpdateDateWhenAlreadyDeleted() throws InterruptedException {
            User user = User.create("test@test.com", "password");
            user.deleteLogically();
            LocalDateTime deletedUpdatedAt = user.getUpdatedAt();

            Thread.sleep(10);

            user.deleteLogically();

            assertAll(
                    () -> assertEquals(UserStatus.DELETED, user.getStatus()),
                    () -> assertEquals(deletedUpdatedAt, user.getUpdatedAt()));
        }
    }

}
