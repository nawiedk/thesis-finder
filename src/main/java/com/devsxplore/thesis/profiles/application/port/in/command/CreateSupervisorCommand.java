package com.devsxplore.thesis.profiles.application.port.in.command;

import com.devsxplore.thesis.profiles.domain.model.Contact;
import com.devsxplore.thesis.profiles.domain.model.Name;

public record CreateSupervisorCommand(Name name, Contact contactDetails) {
    public CreateSupervisorCommand {
        if (name == null || contactDetails == null){
            throw new IllegalArgumentException("Name and contact details cannot be null");
        }
    }
}
