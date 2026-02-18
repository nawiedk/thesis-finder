package com.devsxplore.thesis.supervisors.adapter.in.web.mapper;

import com.devsxplore.thesis.supervisors.adapter.in.web.dto.supervisor.SupervisorCreateDTO;
import com.devsxplore.thesis.supervisors.adapter.in.web.dto.supervisor.SupervisorUpdateDTO;
import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.SupervisorCreateCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.SupervisorDeleteCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.SupervisorUpdateCommand;
import org.springframework.stereotype.Component;

import static com.devsxplore.thesis.supervisors.domain.model.Contact.contactFromPrimitive;
import static com.devsxplore.thesis.supervisors.domain.model.Name.nameFromPrimitive;

@Component
public class SupervisorRequestMapper {

	public static SupervisorCreateCommand generateSupervisorUpdateCommand(Long supervisorUserId,
			SupervisorCreateDTO dto) {
		var name = nameFromPrimitive(dto.firstName(), dto.lastName(), dto.title());
		var contact = contactFromPrimitive(dto.email(), dto.office(), dto.phone());
		return new SupervisorCreateCommand(supervisorUserId, name, contact);
	}

	public static SupervisorUpdateCommand generateSupervisorUpdateCommand(Long supervisorId, SupervisorUpdateDTO dto) {
		return new SupervisorUpdateCommand(supervisorId, dto.firstName(), dto.lastName(), dto.title(), dto.email(),
				dto.office(), dto.phone());
	}

	public static SupervisorDeleteCommand generateSupervisorDeleteCommand(Long supervisorId) {
		return new SupervisorDeleteCommand(supervisorId);
	}

}
