package com.vault.core.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.vault.core.domain.exception.InvalidDomainDataException;

public class UserProfile extends BaseDomainEntity {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String username;
    private String bio;
    private String location;
    private LocalDate birthDate;
    private String avatarUrl;

    private UserProfile(UUID id, UUID userId, String firstName, String lastName, String username,
            String bio, String location, LocalDate birthDate, String avatarUrl) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.bio = bio;
        this.location = location;
        this.birthDate = birthDate;
        this.avatarUrl = avatarUrl;
    }

    public static UserProfile createDefault(UUID userId, String username) {
        if (userId == null) {
            throw new InvalidDomainDataException("User ID cannot be null.");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidDomainDataException("Username cannot be empty.");
        }

        return new UserProfile(
                UUID.randomUUID(),
                userId,
                null,
                null,
                username.trim(),
                "",
                null,
                null,
                null);
    }

    public void updateInfo(String firstName, String lastName, String bio, String location, LocalDate birthDate) {
        if (bio != null && bio.length() > 160) {
            throw new InvalidDomainDataException("Bio cannot exceed 160 characters.");
        }
        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            throw new InvalidDomainDataException("Birth date cannot be in the future.");
        }

        this.firstName = firstName != null && !firstName.trim().isEmpty() ? firstName.trim() : null;
        this.lastName = lastName != null && !lastName.trim().isEmpty() ? lastName.trim() : null;
        this.bio = bio != null ? bio.trim() : "";
        this.location = location != null && !location.trim().isEmpty() ? location.trim() : null;
        this.birthDate = birthDate;

        this.updatedAt = LocalDateTime.now();
    }

    public void updateAvatar(String newAvatarUrl) {
        this.avatarUrl = newAvatarUrl != null && !newAvatarUrl.trim().isEmpty() ? newAvatarUrl.trim() : null;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

}
