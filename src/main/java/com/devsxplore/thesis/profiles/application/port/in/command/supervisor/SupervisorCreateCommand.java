package com.devsxplore.thesis.profiles.application.port.in.command.supervisor;

import com.devsxplore.thesis.profiles.domain.model.Contact;
import com.devsxplore.thesis.profiles.domain.model.Name;

public record SupervisorCreateCommand(Name name, Contact contactDetails) {
    public SupervisorCreateCommand {
        if (name == null || contactDetails == null){
            throw new IllegalArgumentException("Name and contact details cannot be null");
        }
    }
}
