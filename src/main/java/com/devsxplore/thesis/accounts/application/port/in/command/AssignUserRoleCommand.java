package com.devsxplore.thesis.accounts.application.port.in.command;

import com.devsxplore.thesis.accounts.domain.model.UserRole;

public record AssignUserRoleCommand(Long githubId, UserRole role) {
	public AssignUserRoleCommand {
		if (githubId == null || githubId <= 0) {
			throw new IllegalArgumentException("GitHub id must be positive");
		}
		if (role == null) {
			throw new IllegalArgumentException("Role must not be null");
		}
	}
}
