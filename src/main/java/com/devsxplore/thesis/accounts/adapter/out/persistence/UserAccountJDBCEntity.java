package com.devsxplore.thesis.accounts.adapter.out.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("USER_ACCOUNT")
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class UserAccountJDBCEntity implements Persistable<Long> {
    @Transient
    boolean isNew = true;
    @Id
    @NonNull
    private Long githubId;
    @NonNull
    private String login;
    @NonNull
    private String displayName;
    @NonNull
    private String role;
    @NonNull
    private Instant lastLoginAt;

    @Override
    public Long getId() {
        return githubId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void markNotNew() {
        this.isNew = false;
    }
}
