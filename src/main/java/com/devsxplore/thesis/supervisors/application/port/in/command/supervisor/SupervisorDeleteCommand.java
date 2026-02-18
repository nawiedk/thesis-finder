package com.devsxplore.thesis.supervisors.application.port.in.command.supervisor;

import jakarta.validation.constraints.NotNull;

public record SupervisorDeleteCommand(@NotNull Long supervisorUserId) {
}
