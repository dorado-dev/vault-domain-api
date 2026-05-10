package com.vault.core.domain.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.vault.core.domain.exception.IllegalDomainStateException;
import com.vault.core.domain.exception.InvalidDomainDataException;
import com.vault.core.domain.model.enums.PostStatus;

@DisplayName("Tests del Modelo de Dominio: Post")
class PostTest {
    @Nested
    @DisplayName("Método: create()")
    class CreatePostTests {

        @Test
        @DisplayName("Debe crear un Post correctamente con todos los datos válidos")
        void shouldCreatePostWithValidData() {
            UUID authorId = UUID.randomUUID();
            String content = "   Hola Mundo, este es mi post.   ";
            String mediaUrl = " https://imagen.com/foto.jpg ";

            Post post = Post.create(authorId, content, mediaUrl);

            assertAll("Verificación integral del Post creado",
                    () -> assertNotNull(post.getId()),
                    () -> assertEquals(authorId, post.getAuthorId()),
                    () -> assertEquals("Hola Mundo, este es mi post.", post.getContent()),
                    () -> assertEquals("https://imagen.com/foto.jpg", post.getMediaUrl()),
                    () -> assertEquals(PostStatus.PUBLISHED, post.getStatus()),
                    () -> assertNotNull(post.getCreatedAt()));
        }

        @Test
        @DisplayName("Debe crear un Post correctamente aunque la mediaUrl sea nula o vacía")
        void shouldCreatePostWhenMediaUrlIsNull() {
            UUID authorId = UUID.randomUUID();
            String content = "Post sin imagen";

            Post post = Post.create(authorId, content, "   ");

            assertNull(post.getMediaUrl());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el authorId es nulo")
        void shouldThrowExceptionWhenAuthorIdIsNull() {
            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Post.create(null, "Contenido válido", "http://url.com"));

            assertEquals("Author ID cannot be null.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el contenido está vacío")
        void shouldThrowExceptionWhenContentIsEmpty() {
            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Post.create(UUID.randomUUID(), "   ", null));

            assertEquals("Post content cannot be empty.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el contenido supera los 500 caracteres")
        void shouldThrowExceptionWhenContentIsTooLong() {
            UUID authorId = UUID.randomUUID();
            String longContent = "A".repeat(501);

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> Post.create(authorId, longContent, null));

            assertEquals("Post content exceeds maximum allowed length of 500 characters.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Método: editContent()")
    class EditContentTests {

        @Test
        @DisplayName("Debe actualizar el contenido y la fecha si los datos cambian")
        void shouldUpdateContentAndDateWhenDataChanges() throws InterruptedException {
            Post post = Post.create(UUID.randomUUID(), "Texto original", "http://original.com");
            LocalDateTime originalUpdatedAt = post.getUpdatedAt();

            Thread.sleep(10);

            post.editContent("Texto editado", "http://nuevo.com");

            assertAll(
                    () -> assertEquals("Texto editado", post.getContent()),
                    () -> assertEquals("http://nuevo.com", post.getMediaUrl()),
                    () -> assertTrue(post.getUpdatedAt().isAfter(originalUpdatedAt)));
        }

        @Test
        @DisplayName("No debe actualizar nada si los datos son exactamente los mismos")
        void shouldNotUpdateDateWhenDataIsIdentical() throws InterruptedException {
            Post post = Post.create(UUID.randomUUID(), "Texto original", "http://original.com");
            LocalDateTime originalUpdatedAt = post.getUpdatedAt();

            Thread.sleep(10);

            post.editContent("Texto original", "http://original.com");

            assertAll(
                    () -> assertEquals("Texto original", post.getContent()),
                    () -> assertEquals("http://original.com", post.getMediaUrl()),
                    () -> assertEquals(originalUpdatedAt, post.getUpdatedAt()));
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el nuevo contenido está vacío")
        void shouldThrowExceptionWhenNewContentIsEmpty() {
            Post post = Post.create(UUID.randomUUID(), "Texto original", null);

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> post.editContent("   ", null));

            assertEquals("New content cannot be empty.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe lanzar InvalidDomainDataException si el nuevo contenido supera los 500 caracteres")
        void shouldThrowExceptionWhenNewContentIsTooLong() {
            Post post = Post.create(UUID.randomUUID(), "Texto original", null);
            String longContent = "A".repeat(501);

            InvalidDomainDataException exception = assertThrows(
                    InvalidDomainDataException.class,
                    () -> post.editContent(longContent, null));

            assertEquals("New content exceeds maximum allowed length of 500 characters.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Métodos de Cambio de Estado: hide() y makePublic()")
    class StateTransitionTests {

        @Test
        @DisplayName("Debe ocultar el Post y actualizar la fecha si está publicado")
        void shouldHidePostWhenPublished() throws InterruptedException {
            Post post = Post.create(UUID.randomUUID(), "Contenido válido", null);
            LocalDateTime originalUpdatedAt = post.getUpdatedAt();

            Thread.sleep(10);

            post.hide();

            assertAll(
                    () -> assertEquals(PostStatus.HIDDEN, post.getStatus()),
                    () -> assertTrue(post.getUpdatedAt().isAfter(originalUpdatedAt)));
        }

        @Test
        @DisplayName("Debe lanzar IllegalDomainStateException si intentamos ocultar un Post ya oculto")
        void shouldThrowExceptionWhenHidingAlreadyHiddenPost() {
            Post post = Post.create(UUID.randomUUID(), "Contenido válido", null);
            post.hide();

            IllegalDomainStateException exception = assertThrows(
                    IllegalDomainStateException.class,
                    () -> post.hide());

            assertEquals("The post is already hidden.", exception.getMessage());
        }

        @Test
        @DisplayName("Debe hacer público el Post y actualizar la fecha si está oculto")
        void shouldMakePublicWhenHidden() throws InterruptedException {
            Post post = Post.create(UUID.randomUUID(), "Contenido válido", null);
            post.hide();
            LocalDateTime originalUpdatedAt = post.getUpdatedAt();

            Thread.sleep(10);

            post.makePublic();

            assertAll(
                    () -> assertEquals(PostStatus.PUBLISHED, post.getStatus()),
                    () -> assertTrue(post.getUpdatedAt().isAfter(originalUpdatedAt)));
        }

        @Test
        @DisplayName("Debe lanzar IllegalDomainStateException si intentamos publicar un Post ya publicado")
        void shouldThrowExceptionWhenPublishingAlreadyPublishedPost() {
            Post post = Post.create(UUID.randomUUID(), "Contenido válido", null);

            IllegalDomainStateException exception = assertThrows(
                    IllegalDomainStateException.class,
                    () -> post.makePublic());

            assertEquals("The post is already published.", exception.getMessage());
        }
    }

}
