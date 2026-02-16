package com.devsxplore.thesis.supervisors.application.port.in.command.supervisor;

import com.devsxplore.thesis.supervisors.domain.model.Contact;
import com.devsxplore.thesis.supervisors.domain.model.Name;

public record SupervisorCreateCommand(Long supervisorUserId, Name name, Contact contactDetails) {
    public SupervisorCreateCommand {
        if (name == null || contactDetails == null) {
            throw new IllegalArgumentException("Name and contact details cannot be null");
        }
    }
}
