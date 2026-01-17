package com.devsxplore.thesis.profiles.adapter.out.persistence.mapper;

import com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity.SupervisorJDBCEntity;
import com.devsxplore.thesis.profiles.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.devsxplore.thesis.profiles.domain.model.Contact.contactFromPersistence;
import static com.devsxplore.thesis.profiles.domain.model.Name.nameFromPersistence;
import static com.devsxplore.thesis.profiles.domain.model.Supervisor.createSupervisorWithId;

@Component
@RequiredArgsConstructor
public class SupervisorMapper {

    public Supervisor mapToDomainEntity(SupervisorJDBCEntity entity){

        var name = nameFromPersistence(entity.firstName(), entity.lastName(), entity.title());

        var contact = contactFromPersistence(entity.email(), entity.office(), entity.phone());

        return createSupervisorWithId(
                new SupervisorId(entity.id()),
                name,
                contact
        );
    }

    public SupervisorJDBCEntity toJDBCEntity(Supervisor supervisor) {
        return new SupervisorJDBCEntity(
                supervisor.getId(),
                supervisor.getTitleAsString(),
                supervisor.getFirstName(),
                supervisor.getLastName(),
                supervisor.getEmailAsString(),
                supervisor.getOffice(),
                supervisor.getPhone()
        );

    }
}
