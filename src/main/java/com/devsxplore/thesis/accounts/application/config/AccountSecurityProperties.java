package com.devsxplore.thesis.accounts.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

@ConfigurationProperties(prefix = "thesis.security")
public class AccountSecurityProperties {

	private Set<Long> adminGithubIds = new HashSet<>();

	public Set<Long> getAdminGithubIds() {
		return Set.copyOf(adminGithubIds);
	}

	public void setAdminGithubIds(Set<Long> adminGithubIds) {
		this.adminGithubIds = adminGithubIds == null ? new HashSet<>() : new HashSet<>(adminGithubIds);
	}

	public boolean isAdmin(Long githubId) {
		return githubId != null && adminGithubIds.contains(githubId);
	}

}