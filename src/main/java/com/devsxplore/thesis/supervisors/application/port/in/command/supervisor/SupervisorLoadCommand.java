package com.devsxplore.thesis.supervisors.application.port.in.command.supervisor;

import jakarta.validation.constraints.NotNull;

public record SupervisorLoadCommand(@NotNull Long supervisorUserId) {
}
