package com.devsxplore.thesis.accounts.domain.model;

import java.time.Instant;
import java.util.Objects;

public class UserAccount {
    private final UserId userId;
    private String login;
    private String displayName;
    private UserRole role;
    private Instant lastLoginAt;

    private UserAccount(UserId userId, String login, String displayName, UserRole role, Instant lastLoginAt) {
        this.userId = Objects.requireNonNull(userId, "UserId must not be null");
        this.login = normalize(login);
        this.displayName = normalize(displayName) == null ? login : normalize(displayName);
        this.role = Objects.requireNonNull(role, "Role must not be null");
        this.lastLoginAt = lastLoginAt;
    }

    public static UserAccount createUserAccountWithId(Long userId, String login, String displayName) {
        return new UserAccount(UserId.of(userId), login, displayName, UserRole.STUDENT, Instant.now());
    }

    public static UserAccount restore(Long userId, String login, String displayName, String role, Instant lastLoginAt) {
        return new UserAccount(UserId.of(userId), login, displayName, UserRole.valueOf(role), lastLoginAt);
    }

    public Long getUserId() {
        return userId.userId();
    }

    public String getLogin() {
        return login;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UserRole getRole() {
        return role;
    }

    public String getRoleAsString() {
        return role.name();
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }

    public void updateProfile(String login, String displayName) {
        String normalizedLogin = normalize(login);
        if (normalizedLogin != null) {
            this.login = normalizedLogin;
        }
        String normalizedDisplayName = normalize(displayName);
        if (normalizedDisplayName != null) {
            this.displayName = normalizedDisplayName;
        }
        this.lastLoginAt = Instant.now();
    }

    public void assignRole(UserRole role) {
        this.role = Objects.requireNonNull(role, "Role must not be null");
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
