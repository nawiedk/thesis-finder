package com.devsxplore.thesis.accounts.application.port.in.command;

public record LoadAccountCommand(Long githubId) {
    public LoadAccountCommand {
        if (githubId == null || githubId <= 0) {
            throw new IllegalArgumentException("GitHub id must be positive");
        }
    }
}
