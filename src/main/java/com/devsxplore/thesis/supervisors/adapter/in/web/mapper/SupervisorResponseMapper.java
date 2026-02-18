package com.devsxplore.thesis.supervisors.adapter.in.web.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import com.devsxplore.thesis.supervisors.adapter.in.web.dto.response.SupervisorProfileDTO;
import com.devsxplore.thesis.supervisors.domain.model.FieldTag;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;
import org.springframework.stereotype.Component;

@Component
public class SupervisorResponseMapper {

	public SupervisorProfileDTO mapToSupervisorProfile(Supervisor supervisor) {
		Set<String> fields = supervisor.getFields()
				.stream()
				.map(FieldTag::fieldName).
				collect(Collectors.toSet());

		return new SupervisorProfileDTO(supervisor.getPublicId(),
				supervisor.getFullName(),
				supervisor.getEmailAsString(),
				supervisor.getOffice(), supervisor.getPhone(),
				fields);
	}

	public Set<SupervisorProfileDTO> mapToSupervisorProfiles(Set<Supervisor> supervisors) {
		return supervisors.stream()
				.map(this::mapToSupervisorProfile)
				.collect(Collectors.toSet());
	}

}
