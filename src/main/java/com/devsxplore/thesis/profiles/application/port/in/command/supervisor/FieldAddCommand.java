package com.devsxplore.thesis.profiles.application.port.in.command.supervisor;

public record FieldAddCommand(
        Long supervisorId,
        String fieldName
) {
}
