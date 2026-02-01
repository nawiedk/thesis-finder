package com.devsxplore.thesis.profiles.application.port.in.command.supervisor;

import jakarta.validation.constraints.NotNull;

public record SupervisorDeleteCommand(
        @NotNull
        Long supervisorId
) {
}
