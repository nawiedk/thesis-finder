package com.devsxplore.thesis.supervisors.adapter.in.web.mapper;

import com.devsxplore.thesis.supervisors.adapter.in.web.dto.response.SupervisorProfileDTO;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;
import org.springframework.stereotype.Component;

@Component
public class SupervisorResponseMapper {

    public SupervisorProfileDTO mapToSupervisorProfileDTO(Supervisor supervisor) {
        return new SupervisorProfileDTO(
                supervisor.getPublicId(),
                supervisor.getFullName(),
                supervisor.getEmailAsString(),
                supervisor.getOffice(),
                supervisor.getPhone()
        );
    }
}
