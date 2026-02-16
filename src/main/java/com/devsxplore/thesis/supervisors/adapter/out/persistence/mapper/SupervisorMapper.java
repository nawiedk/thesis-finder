package com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper;

import com.devsxplore.thesis.supervisors.adapter.out.persistence.jdbcentity.SupervisorJDBCEntity;
import com.devsxplore.thesis.supervisors.domain.model.*;
import org.springframework.stereotype.Component;

import static com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper.FieldTagMapper.mapFieldTagsToDomainEntities;
import static com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper.FieldTagMapper.mapFieldTagsToJDBCEntities;
import static com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper.TopicMapper.mapTopicsToDomainEntities;
import static com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper.TopicMapper.mapTopicsToJDBCEntities;
import static com.devsxplore.thesis.supervisors.domain.model.Contact.contactFromPrimitive;
import static com.devsxplore.thesis.supervisors.domain.model.Name.nameFromPrimitive;
import static com.devsxplore.thesis.supervisors.domain.model.Supervisor.createSupervisorWithId;

@Component
public class SupervisorMapper {

    public Supervisor mapSupervisorToDomainEntity(SupervisorJDBCEntity entity) {
        Name name = nameFromPrimitive(entity.firstName(), entity.lastName(), entity.title());
        Contact contactDetails = contactFromPrimitive(entity.email(), entity.office(), entity.phone());

        Supervisor supervisor = createSupervisorWithId(
                new SupervisorId(entity.supervisorId()),
                new UserId(entity.userId()),
                new PublicId(entity.publicId()),
                name,
                contactDetails
        );

        if (entity.fields() != null) {
            mapFieldTagsToDomainEntities(entity.fields())
                    .forEach(supervisor::addExistingFields);
        }

        if (entity.topics() != null) {
            mapTopicsToDomainEntities(entity.topics())
                    .forEach(supervisor::addExistingTopic);
        }

        return supervisor;
    }

    public SupervisorJDBCEntity mapSupervisorToJDBCEntity(Supervisor entity) {
        return new SupervisorJDBCEntity(
                entity.getSupervisorId(),
                entity.getUserID(),
                entity.getPublicId(),
                entity.getAcademicTitle(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmailAsString(),
                entity.getOffice(),
                entity.getPhone(),
                mapFieldTagsToJDBCEntities(entity.getFields()),
                mapTopicsToJDBCEntities(entity.getTopics())
        );

    }
}