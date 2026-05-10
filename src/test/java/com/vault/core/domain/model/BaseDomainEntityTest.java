package com.vault.core.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests del Modelo de Dominio: BaseDomainEntity")
class BaseDomainEntityTest {
    static class DummyEntity extends BaseDomainEntity {
        public DummyEntity(UUID id) {
            this.id = id;
        }
    }

    static class AnotherDummyEntity extends BaseDomainEntity {
        public AnotherDummyEntity(UUID id) {
            this.id = id;
        }
    }

    @Nested
    @DisplayName("Método: equals() y hashCode()")
    class EqualsAndHashCodeTests {

        @Test
        @DisplayName("Debe ser igual a sí mismo (Reflexividad)")
        void shouldBeEqualUntoItself() {
            DummyEntity entity = new DummyEntity(UUID.randomUUID());
            assertEquals(entity, entity);
        }

        @Test
        @DisplayName("Deben ser iguales y tener el mismo hashCode si tienen el mismo ID")
        void shouldBeEqualIfIdsAreTheSame() {
            UUID sharedId = UUID.randomUUID();
            DummyEntity entity1 = new DummyEntity(sharedId);
            DummyEntity entity2 = new DummyEntity(sharedId);

            assertEquals(entity1, entity2);
            assertEquals(entity1.hashCode(), entity2.hashCode());
        }

        @Test
        @DisplayName("No deben ser iguales si tienen IDs diferentes")
        void shouldNotBeEqualIfIdsAreDifferent() {
            DummyEntity entity1 = new DummyEntity(UUID.randomUUID());
            DummyEntity entity2 = new DummyEntity(UUID.randomUUID());

            assertNotEquals(entity1, entity2);
        }

        @Test
        @DisplayName("No debe ser igual a null")
        void shouldNotBeEqualToNull() {
            DummyEntity entity = new DummyEntity(UUID.randomUUID());
            assertNotEquals(null, entity);
        }

        @Test
        @DisplayName("No deben ser iguales si son de clases distintas aunque tengan el mismo ID")
        void shouldNotBeEqualIfClassesAreDifferent() {
            UUID sharedId = UUID.randomUUID();
            DummyEntity entity1 = new DummyEntity(sharedId);
            AnotherDummyEntity entity2 = new AnotherDummyEntity(sharedId);

            assertNotEquals(entity1, entity2);
        }
    }

}
