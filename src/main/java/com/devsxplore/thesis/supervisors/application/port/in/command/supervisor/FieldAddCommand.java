package com.devsxplore.thesis.supervisors.application.port.in.command.supervisor;

public record FieldAddCommand(Long supervisorUserId, String fieldName) {
}
