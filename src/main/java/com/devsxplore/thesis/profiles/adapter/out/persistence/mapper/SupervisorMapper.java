package com.devsxplore.thesis.profiles.adapter.out.persistence.mapper;

import com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity.SupervisorJDBCEntity;
import com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity.TopicJDBCEntity;
import com.devsxplore.thesis.profiles.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.devsxplore.thesis.profiles.domain.model.Contact.contactFromPersistence;
import static com.devsxplore.thesis.profiles.domain.model.Name.nameFromPersistence;
import static com.devsxplore.thesis.profiles.domain.model.Supervisor.createSupervisorWithId;

@Component
@RequiredArgsConstructor
public class SupervisorMapper {

    public Supervisor mapToDomainEntity(SupervisorJDBCEntity entity){

        var name = nameFromPersistence(entity.firstName(), entity.lastName(), entity.title());

        var contact = contactFromPersistence(entity.email(), entity.office(), entity.phone());

        var supervisor = createSupervisorWithId(
                new SupervisorId(entity.id()),
                name,
                contact
        );

        List<Topic> topics = entity.topics().stream().map(TopicMapper::mapToDomainEntity).toList();

        topics.forEach(topic -> supervisor.addTopic(topic.getTitle(), topic.getDescription()));

        return supervisor;
    }

    public SupervisorJDBCEntity toJDBCEntity(Supervisor supervisor) {
        List<TopicJDBCEntity> topicJDBCEntityList = supervisor.getTopics().stream()
                .map(TopicMapper::toJDBCEntity)
                .toList();

        return new SupervisorJDBCEntity(
                supervisor.getId(),
                supervisor.getTitleAsString(),
                supervisor.getFirstName(),
                supervisor.getLastName(),
                supervisor.getEmailAsString(),
                supervisor.getOffice(),
                supervisor.getPhone(),
                topicJDBCEntityList
        );

    }
}
