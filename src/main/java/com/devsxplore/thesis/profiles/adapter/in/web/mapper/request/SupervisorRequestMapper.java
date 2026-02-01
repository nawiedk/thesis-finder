package com.devsxplore.thesis.profiles.adapter.in.web.mapper.request;


import com.devsxplore.thesis.profiles.adapter.in.web.dto.supervisor.SupervisorCreateDTO;
import com.devsxplore.thesis.profiles.adapter.in.web.dto.supervisor.SupervisorUpdateDTO;
import com.devsxplore.thesis.profiles.application.port.in.command.supervisor.SupervisorCreateCommand;
import com.devsxplore.thesis.profiles.application.port.in.command.supervisor.SupervisorDeleteCommand;
import com.devsxplore.thesis.profiles.application.port.in.command.supervisor.SupervisorUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.devsxplore.thesis.profiles.domain.model.Contact.contactFromPrimitive;
import static com.devsxplore.thesis.profiles.domain.model.Name.nameFromPrimitive;

@Component
@RequiredArgsConstructor
public class SupervisorRequestMapper {


    public static SupervisorCreateCommand generateSupervisorUpdateCommand(SupervisorCreateDTO dto) {
        var name = nameFromPrimitive(dto.firstName(), dto.lastName(), dto.title());
        var contact = contactFromPrimitive(dto.email(), dto.office(), dto.phone());
        return new SupervisorCreateCommand(name, contact);
    }

    public static SupervisorUpdateCommand generateSupervisorUpdateCommand(Long supervisorId, SupervisorUpdateDTO dto) {
        return new SupervisorUpdateCommand(supervisorId, dto.firstName(), dto.lastName(), dto.title(), dto.email(), dto.office(), dto.phone());
    }

    public static SupervisorDeleteCommand generateSupervisorDeleteCommand(Long supervisorId) {
        return new SupervisorDeleteCommand(supervisorId);
    }
}
