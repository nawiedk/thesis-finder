package com.devsxplore.thesis.accounts.application.port.in.command;

public record RegisterAccountCommand(Long githubId, String login, String displayName) {
    public RegisterAccountCommand {
        if (githubId == null || githubId <= 0) {
            throw new IllegalArgumentException("GitHub id must be positive");
        }
    }
}
