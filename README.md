# Vault: Core Domain API

[![Java 21](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)](https://jdk.java.net/21/)
[![Spring Boot 4.0](https://img.shields.io/badge/Spring_Boot-4.0.5-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL 18](https://img.shields.io/badge/PostgreSQL-18-4169E1?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?logo=docker&logoColor=white)](https://www.docker.com/)

## Project Overview

Vault Core Domain API is a high-decoupling backend infrastructure designed as the central orchestration engine for a social platform. Developed under a rigorous domain-centric paradigm, its primary objective is the secure management of business entities and state transitions, maintaining absolute isolation from external frameworks and persistence mechanisms.

This repository serves as a comprehensive blueprint for professional-grade software development. It demonstrates the pragmatic application of Clean Architecture and SOLID principles to ensure systemic maintainability, technical portability, and a high-reliability testing environment.

## Architectural Blueprint

The system is strictly structured following Hexagonal Architecture (Ports and Adapters) to guarantee a clear separation of concerns. The flow of dependencies always points inwards toward the core domain.

* **Domain Layer:** The heart of the application. It contains rich business models, custom exceptions, and port definitions (interfaces). This layer is written in pure Java and remains completely agnostic to any framework, database, or external library.
* **Application Layer:** Orchestrates business operations through highly cohesive Use Cases rather than generic service implementations. It coordinates the domain logic and establishes transactional boundaries, using the domain ports to communicate with the outside world.
* **Infrastructure Layer:** Contains all technical details and framework-coupled code.
  * **Driving Adapters (Input):** REST controllers, global exception handlers, and DTO mappers handling incoming HTTP requests.
  * **Driven Adapters (Output):** Repository implementations (Spring Data JPA), external API clients, and persistence mappers connecting to PostgreSQL 18.
  * **Configuration:** Spring `@Configuration` classes responsible for dependency injection, keeping the application and domain layers free from Spring annotations.

## Integrated Ecosystem

The technology stack is strategically selected to support the architectural boundaries and ensure a robust, production-ready environment:

* **Runtime & Framework:** Java 21 and Spring Boot 4.0.x, providing a modern, high-performance foundation.
* **Security Layer:** Spring Security combined with JJWT for a robust, stateless authentication mechanism.
* **Persistence & Versioning:** PostgreSQL 18 handled via Spring Data JPA, with Flyway strictly managing automated database schema migrations.
* **Data Mapping:** MapStruct for type-safe, compile-time DTO-to-Entity conversions, preventing domain model leakage into the infrastructure layer.
* **Quality Assurance:** JUnit 5 and Mockito driving the testing strategy, focusing on high-coverage unit testing for business rules and use cases.
* **Containerization:** Docker for full service orchestration, guaranteeing absolute consistency between local development and production environments.

## Execution & Portability

The project is engineered for immediate environment portability. All infrastructure dependencies are containerized to ensure strict consistency across the development lifecycle, preventing environment-specific discrepancies.

### Infrastructure Provisioning
The persistence layer and network isolation are managed via Docker orchestration. To initialize the PostgreSQL 18 instance and the required infrastructure, execute the following command from the root directory:

```bash
docker-compose up -d
```

### Local Development Lifecycle

Once the infrastructure layer is active, use the Maven build system to manage the application lifecycle. This process includes dependency resolution, core domain compilation, test suite execution, and service bootstrapping:

```bash
mvn clean install
mvn spring-boot:run
```

Upon successful initialization, the API will bind to http://localhost:8080/api/v1.

## Security & Configuration Management

The application strictly adheres to the 12-Factor App methodology regarding configuration. All sensitive data, cryptographic keys, and environment-specific parameters are injected at runtime via environment variables. A `.env.example` file is provided in the repository root as a template.

Required environment variables:

* `SPRING_PROFILES_ACTIVE`: Defines the execution environment profile (e.g., dev, test, prod).
* `DB_URL`: The JDBC connection string for the PostgreSQL 18 instance.
* `DB_USERNAME`: Database authentication user.
* `DB_PASSWORD`: Database authentication password.
* `JWT_SECRET_KEY`: A highly secure, Base64-encoded 256-bit key used for HMAC-SHA256 signature generation.
* `JWT_EXPIRATION_MS`: Token validity duration expressed in milliseconds.
* `FLYWAY_ENABLED`: Boolean toggle to control automated schema migrations during startup.


## Quality Standards & Contribution

To maintain the architectural integrity of the system, any new feature or pull request must adhere to the following standards:

* **Clean Code:** Strict compliance with single-responsibility principles and meaningful naming conventions. No "magic numbers" or hardcoded values.
* **Testing Pyramid:** High test coverage is mandatory. Unit tests must cover Domain entities and Use Cases using JUnit 5 and Mockito.
* **Architectural Boundaries:** Business logic must never leak into controllers or repositories. The Domain layer must remain free of Spring annotations.
* **API Contracts:** All REST endpoints must be consistently documented via Swagger/OpenAPI.

---

<br>

> "A good architecture allows major decisions to be deferred." — Robert C. Martin