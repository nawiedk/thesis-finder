package com.devsxplore.thesis.accounts.adapter.out.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("USER_ACCOUNT")
public record UserAccountJDBCEntity(
        @Id Long githubId,
        String login,
        String displayName,
        String role,
        Instant lastLoginAt
) {
}
