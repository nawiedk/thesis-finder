package com.devsxplore.thesis.profiles.application.port.in.command.supervisor;

public record SupervisorUpdateCommand(Long supervisorId, String firstName, String lastName, String title, String email, String office, String phone) {
    public SupervisorUpdateCommand {
    }
}
