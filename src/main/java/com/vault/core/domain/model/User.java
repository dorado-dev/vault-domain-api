package com.vault.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vault.core.domain.exception.IllegalDomainStateException;
import com.vault.core.domain.exception.InvalidDomainDataException;
import com.vault.core.domain.model.enums.RoleType;
import com.vault.core.domain.model.enums.UserStatus;

public class User extends BaseDomainEntity {
    private String email;
    private String password;
    private RoleType role;
    private UserStatus status;

    private User(UUID id, String email, String password, RoleType role, UserStatus status) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public static User create(String email, String passwordHash) {
        if (email == null || !email.contains("@")) {
            throw new InvalidDomainDataException("A valid email is required.");
        }
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            throw new InvalidDomainDataException("Password cannot be empty.");
        }

        return new User(
                UUID.randomUUID(),
                email.toLowerCase().trim(),
                passwordHash,
                RoleType.USER,
                UserStatus.ACTIVE);
    }

    public void updatePassword(String newPasswordHash) {
        if (newPasswordHash == null || newPasswordHash.trim().isEmpty()) {
            throw new InvalidDomainDataException("New password cannot be empty.");
        }
        if (this.password.equals(newPasswordHash)) {
            throw new InvalidDomainDataException("New password must be different from the current one.");
        }
        this.password = newPasswordHash;
        this.updatedAt = LocalDateTime.now();
    }

    public void suspend() {
        if (this.status == UserStatus.DELETED) {
            throw new IllegalDomainStateException("Cannot suspend a deleted user.");
        }
        if (this.status == UserStatus.SUSPENDED) {
            return;
        }
        this.status = UserStatus.SUSPENDED;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        if (this.status == UserStatus.DELETED) {
            throw new IllegalDomainStateException("Cannot activate a deleted user.");
        }
        if (this.status == UserStatus.ACTIVE) {
            return;
        }
        this.status = UserStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void deleteLogically() {
        if (this.status == UserStatus.DELETED) {
            return;
        }
        this.status = UserStatus.DELETED;
        this.updatedAt = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public RoleType getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

}
