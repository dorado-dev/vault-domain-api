package com.vault.core.domain.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.vault.core.domain.exception.InvalidDomainDataException;

@Nested
@DisplayName("Método: create()")
class FollowTest {
    @Nested
    @DisplayName("Método: create()")
    class CreateFollowTests {

        @Test
        @DisplayName("Debe crear un Follow correctamente con datos válidos")
        void shouldCreateFollowWithValidData() {
            UUID followerId = UUID.randomUUID();
            UUID followingId = UUID.randomUUID();

            Follow follow = Follow.create(followerId, followingId);

            assertAll(
                    () -> assertNotNull(follow.getId()),
                    () -> assertEquals(followerId, follow.getFollowerId()),
                    () -> assertEquals(followingId, follow.getFollowingId()),
                    () -> assertNotNull(follow.getCreatedAt()));
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el followerId es nulo")
        void shouldThrowExceptionWhenFollowerIdIsNull() {
            UUID followingId = UUID.randomUUID();

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Follow.create(null, followingId));

            assertEquals("Follower ID and Following ID cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el followingId es nulo")
        void shouldThrowExceptionWhenFollowingIdIsNull() {
            UUID followerId = UUID.randomUUID();

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Follow.create(followerId, null));

            assertEquals("Follower ID and Following ID cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si un usuario intenta seguirse a sí mismo")
        void shouldThrowExceptionWhenUserFollowsThemselves() {
            UUID userId = UUID.randomUUID();

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Follow.create(userId, userId));

            assertEquals("A user cannot follow themselves.", exception.getMessage());
        }
    }

}
