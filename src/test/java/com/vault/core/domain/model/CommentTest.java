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

@DisplayName("Tests del Modelo de Dominio: Comment")
class CommentTest {
    @Nested
    @DisplayName("Método: create()")
    class CreateCommentTests {

        @Test
        @DisplayName("Debe crear un Comment correctamente con todos los datos válidos")
        void shouldCreateCommentWithValidData() {
            UUID authorId = UUID.randomUUID();
            UUID postId = UUID.randomUUID();
            String content = "   Este es un comentario excelente.   ";

            Comment comment = Comment.create(authorId, postId, content);

            assertAll(
                    () -> assertNotNull(comment.getId()),
                    () -> assertEquals(authorId, comment.getAuthorId()),
                    () -> assertEquals(postId, comment.getPostId()),
                    () -> assertEquals("Este es un comentario excelente.", comment.getContent()),
                    () -> assertNotNull(comment.getCreatedAt()));
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el authorId es nulo")
        void shouldThrowExceptionWhenAuthorIdIsNull() {
            UUID postId = UUID.randomUUID();

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Comment.create(null, postId, "Contenido válido"));

            assertEquals("Author ID and Post ID cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el postId es nulo")
        void shouldThrowExceptionWhenPostIdIsNull() {
            UUID authorId = UUID.randomUUID();

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Comment.create(authorId, null, "Contenido válido"));

            assertEquals("Author ID and Post ID cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el contenido está vacío")
        void shouldThrowExceptionWhenContentIsEmpty() {
            UUID authorId = UUID.randomUUID();
            UUID postId = UUID.randomUUID();

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Comment.create(authorId, postId, "   "));

            assertEquals("Comment content cannot be empty.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el contenido supera los 300 caracteres")
        void shouldThrowExceptionWhenContentIsTooLong() {
            UUID authorId = UUID.randomUUID();
            UUID postId = UUID.randomUUID();
            String longContent = "A".repeat(301);

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Comment.create(authorId, postId, longContent));

            assertEquals("Comment exceeds maximum allowed length of 300 characters.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Método: editContent()")
    class EditContentTests {

        @Test
        @DisplayName("Debe actualizar el contenido y la fecha si los datos cambian")
        void shouldUpdateContentAndDateWhenDataChanges() throws InterruptedException {
            Comment comment = Comment.create(UUID.randomUUID(), UUID.randomUUID(), "Texto original");
            LocalDateTime originalUpdatedAt = comment.getUpdatedAt();

            Thread.sleep(10);

            comment.editContent("   Texto editado   ");

            assertAll(
                    () -> assertEquals("Texto editado", comment.getContent()),
                    () -> assertTrue(comment.getUpdatedAt().isAfter(originalUpdatedAt)));
        }

        @Test
        @DisplayName("No debe actualizar nada si el contenido es exactamente el mismo")
        void shouldNotUpdateDateWhenDataIsIdentical() throws InterruptedException {
            Comment comment = Comment.create(UUID.randomUUID(), UUID.randomUUID(), "Texto original");
            LocalDateTime originalUpdatedAt = comment.getUpdatedAt();

            Thread.sleep(10);

            comment.editContent("Texto original");

            assertAll(
                    () -> assertEquals("Texto original", comment.getContent()),
                    () -> assertEquals(originalUpdatedAt, comment.getUpdatedAt()));
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el nuevo contenido está vacío")
        void shouldThrowExceptionWhenNewContentIsEmpty() {
            Comment comment = Comment.create(UUID.randomUUID(), UUID.randomUUID(), "Texto original");

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> comment.editContent("   "));

            assertEquals("New content cannot be empty.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el nuevo contenido supera los 300 caracteres")
        void shouldThrowExceptionWhenNewContentIsTooLong() {
            Comment comment = Comment.create(UUID.randomUUID(), UUID.randomUUID(), "Texto original");
            String longContent = "A".repeat(301);

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> comment.editContent(longContent));

            assertEquals("New content exceeds maximum allowed length of 300 characters.", exception.getMessage());
        }
    }

}
