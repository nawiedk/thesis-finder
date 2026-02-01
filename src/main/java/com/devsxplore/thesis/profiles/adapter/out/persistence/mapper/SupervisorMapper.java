package com.devsxplore.thesis.profiles.adapter.out.persistence.mapper;

import com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity.SupervisorJDBCEntity;
import com.devsxplore.thesis.profiles.domain.model.Contact;
import com.devsxplore.thesis.profiles.domain.model.Name;
import com.devsxplore.thesis.profiles.domain.model.Supervisor;
import com.devsxplore.thesis.profiles.domain.model.SupervisorId;

import static com.devsxplore.thesis.profiles.adapter.out.persistence.mapper.FieldTagMapper.mapFieldTagsToDomainEntities;
import static com.devsxplore.thesis.profiles.adapter.out.persistence.mapper.FieldTagMapper.mapFieldTagsToJDBCEntities;
import static com.devsxplore.thesis.profiles.adapter.out.persistence.mapper.TopicMapper.mapTopicsToDomainEntities;
import static com.devsxplore.thesis.profiles.adapter.out.persistence.mapper.TopicMapper.mapTopicsToJDBCEntities;
import static com.devsxplore.thesis.profiles.domain.model.Contact.contactFromPrimitive;
import static com.devsxplore.thesis.profiles.domain.model.Name.nameFromPrimitive;
import static com.devsxplore.thesis.profiles.domain.model.Supervisor.createSupervisorWithId;

public class SupervisorMapper {

    public static Supervisor mapSupervisorToDomainEntity(SupervisorJDBCEntity entity) {
        Name name = nameFromPrimitive(entity.firstName(), entity.lastName(), entity.title());
        Contact contactDetails = contactFromPrimitive(entity.email(), entity.office(), entity.phone());

        Supervisor supervisor = createSupervisorWithId(
                new SupervisorId(entity.id()),
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

    public static SupervisorJDBCEntity mapSupervisorToJDBCEntity(Supervisor entity) {
        return new SupervisorJDBCEntity(
                entity.getSupervisorId(),
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
