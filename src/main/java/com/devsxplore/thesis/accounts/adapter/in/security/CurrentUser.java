package com.devsxplore.thesis.accounts.adapter.in.security;

import jakarta.annotation.Nonnull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public record CurrentUser(
        Long githubId,
        String login,
        String name,
        Map<String, Object> attributes,
        Set<GrantedAuthority> authorities
) implements OAuth2User {

    public CurrentUser {
        Objects.requireNonNull(githubId, "githubId must not be null");
        Objects.requireNonNull(login, "login must not be null");

        authorities = Set.copyOf(authorities != null ? authorities : Set.of());
        attributes = Map.copyOf(attributes != null ? attributes : Map.of());
        if (name == null)
            name = login;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @Nonnull
    public String getName() {
        return login;
    }
}
