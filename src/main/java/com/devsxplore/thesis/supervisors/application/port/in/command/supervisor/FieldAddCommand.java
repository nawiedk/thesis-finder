package com.devsxplore.thesis.supervisors.application.port.in.command.supervisor;

public record FieldAddCommand(
        Long supervisorId,
        String fieldName
) {
}
