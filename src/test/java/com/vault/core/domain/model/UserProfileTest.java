package com.vault.core.domain.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.vault.core.domain.exception.InvalidDomainDataException;

@DisplayName("Tests del Modelo de Dominio: UserProfile")
class UserProfileTest {
    @Nested
    @DisplayName("Método: createDefault()")
    class CreateDefaultTests {

        @Test
        @DisplayName("Debe crear un perfil por defecto con los campos inicializados correctamente")
        void shouldCreateDefaultProfileWithValidData() {
            UUID userId = UUID.randomUUID();
            String username = "   john_doe   ";

            UserProfile profile = UserProfile.createDefault(userId, username);

            assertAll(
                    () -> assertNotNull(profile.getId()),
                    () -> assertEquals(userId, profile.getUserId()),
                    () -> assertEquals("john_doe", profile.getUsername()),
                    () -> assertEquals("", profile.getBio()),
                    () -> assertNull(profile.getFirstName()),
                    () -> assertNull(profile.getLastName()),
                    () -> assertNull(profile.getLocation()),
                    () -> assertNull(profile.getBirthDate()),
                    () -> assertNull(profile.getAvatarUrl()),
                    () -> assertNotNull(profile.getCreatedAt()));
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el userId es nulo")
        void shouldThrowExceptionWhenUserIdIsNull() {
            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> UserProfile.createDefault(null, "username"));

            assertEquals("User ID cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el username está vacío")
        void shouldThrowExceptionWhenUsernameIsEmpty() {
            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> UserProfile.createDefault(UUID.randomUUID(), "   "));

            assertEquals("Username cannot be empty.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Método: updateInfo()")
    class UpdateInfoTests {

        @Test
        @DisplayName("Debe actualizar la información y limpiar los espacios en blanco")
        void shouldUpdateInfoAndSanitizeInputs() throws InterruptedException {
            UserProfile profile = UserProfile.createDefault(UUID.randomUUID(), "username");
            LocalDateTime originalUpdatedAt = profile.getUpdatedAt();
            LocalDate birthDate = LocalDate.of(1990, 5, 15);

            Thread.sleep(10);

            profile.updateInfo(" John ", " Doe ", " Mi nueva bio ", " Madrid ", birthDate);

            assertAll(
                    () -> assertEquals("John", profile.getFirstName()),
                    () -> assertEquals("Doe", profile.getLastName()),
                    () -> assertEquals("Mi nueva bio", profile.getBio()),
                    () -> assertEquals("Madrid", profile.getLocation()),
                    () -> assertEquals(birthDate, profile.getBirthDate()),
                    () -> assertTrue(profile.getUpdatedAt().isAfter(originalUpdatedAt)));
        }

        @Test
        @DisplayName("Debe normalizar los campos a null o string vacío si reciben datos nulos o en blanco")
        void shouldNormalizeToNullOrEmptyWhenDataIsBlank() {
            UserProfile profile = UserProfile.createDefault(UUID.randomUUID(), "username");

            profile.updateInfo("   ", null, null, "   ", null);

            assertAll(
                    () -> assertNull(profile.getFirstName()),
                    () -> assertNull(profile.getLastName()),
                    () -> assertEquals("", profile.getBio()),
                    () -> assertNull(profile.getLocation()),
                    () -> assertNull(profile.getBirthDate()));
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si la bio supera los 160 caracteres")
        void shouldThrowExceptionWhenBioIsTooLong() {
            UserProfile profile = UserProfile.createDefault(UUID.randomUUID(), "username");
            String longBio = "A".repeat(161);

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> profile.updateInfo("John", "Doe", longBio, "Madrid", LocalDate.now()));

            assertEquals("Bio cannot exceed 160 characters.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si la fecha de nacimiento es en el futuro")
        void shouldThrowExceptionWhenBirthDateIsInTheFuture() {
            UserProfile profile = UserProfile.createDefault(UUID.randomUUID(), "username");
            LocalDate futureDate = LocalDate.now().plusDays(1);

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> profile.updateInfo("John", "Doe", "Bio", "Madrid", futureDate));

            assertEquals("Birth date cannot be in the future.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Método: updateAvatar()")
    class UpdateAvatarTests {

        @Test
        @DisplayName("Debe actualizar el avatar si la URL es válida")
        void shouldUpdateAvatarUrl() throws InterruptedException {
            UserProfile profile = UserProfile.createDefault(UUID.randomUUID(), "username");
            LocalDateTime originalUpdatedAt = profile.getUpdatedAt();

            Thread.sleep(10);

            profile.updateAvatar(" https://avatar.com/foto.jpg ");

            assertAll(
                    () -> assertEquals("https://avatar.com/foto.jpg", profile.getAvatarUrl()),
                    () -> assertTrue(profile.getUpdatedAt().isAfter(originalUpdatedAt)));
        }

        @Test
        @DisplayName("Debe establecer el avatar como null si la URL es vacía o nula")
        void shouldSetAvatarToNullWhenEmpty() {
            UserProfile profile = UserProfile.createDefault(UUID.randomUUID(), "username");
            profile.updateAvatar("https://avatar.com/foto.jpg");

            profile.updateAvatar("   ");

            assertNull(profile.getAvatarUrl());
        }
    }

}
