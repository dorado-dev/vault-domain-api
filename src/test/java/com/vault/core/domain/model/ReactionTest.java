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

import com.vault.core.domain.exception.InvalidDomainDataException;
import com.vault.core.domain.model.enums.ReactionType;

@DisplayName("Tests del Modelo de Dominio: Reaction")
class ReactionTest {
    @Nested
    @DisplayName("Método: create()")
    class CreateReactionTests {

        @Test
        @DisplayName("Debe crear una Reaction correctamente con datos válidos")
        void shouldCreateReactionWithValidData() {
            UUID userId = UUID.randomUUID();
            UUID postId = UUID.randomUUID();
            ReactionType type = ReactionType.values()[0];

            Reaction reaction = Reaction.create(userId, postId, type);

            assertAll(
                    () -> assertNotNull(reaction.getId()),
                    () -> assertEquals(userId, reaction.getUserId()),
                    () -> assertEquals(postId, reaction.getPostId()),
                    () -> assertEquals(type, reaction.getType()),
                    () -> assertNotNull(reaction.getCreatedAt()));
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el userId es nulo")
        void shouldThrowExceptionWhenUserIdIsNull() {
            UUID postId = UUID.randomUUID();
            ReactionType type = ReactionType.values()[0];

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Reaction.create(null, postId, type));

            assertEquals("User ID and Post ID cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el postId es nulo")
        void shouldThrowExceptionWhenPostIdIsNull() {
            UUID userId = UUID.randomUUID();
            ReactionType type = ReactionType.values()[0];

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Reaction.create(userId, null, type));

            assertEquals("User ID and Post ID cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el type es nulo")
        void shouldThrowExceptionWhenTypeIsNull() {
            UUID userId = UUID.randomUUID();
            UUID postId = UUID.randomUUID();

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Reaction.create(userId, postId, null));

            assertEquals("A reaction type must be provided.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Método: changeType()")
    class ChangeTypeTests {

        @Test
        @DisplayName("Debe actualizar el tipo y la fecha si el nuevo tipo es diferente")
        void shouldUpdateTypeAndDateWhenTypeChanges() throws InterruptedException {
            ReactionType initialType = ReactionType.values()[0];
            ReactionType newType = ReactionType.values()[ReactionType.values().length - 1];

            Reaction reaction = Reaction.create(UUID.randomUUID(), UUID.randomUUID(), initialType);
            LocalDateTime originalUpdatedAt = reaction.getUpdatedAt();

            Thread.sleep(10);

            reaction.changeType(newType);

            assertAll(
                    () -> assertEquals(newType, reaction.getType()),
                    () -> assertTrue(reaction.getUpdatedAt().isAfter(originalUpdatedAt)));
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el nuevo tipo es nulo")
        void shouldThrowExceptionWhenNewTypeIsNull() {
            Reaction reaction = Reaction.create(UUID.randomUUID(), UUID.randomUUID(), ReactionType.values()[0]);

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> reaction.changeType(null));

            assertEquals("The new reaction type cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el nuevo tipo es idéntico al actual")
        void shouldThrowExceptionWhenNewTypeIsIdentical() {
            ReactionType type = ReactionType.values()[0];
            Reaction reaction = Reaction.create(UUID.randomUUID(), UUID.randomUUID(), type);

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> reaction.changeType(type));

            assertEquals("The reaction is already of this type.", exception.getMessage());
        }
    }

}
