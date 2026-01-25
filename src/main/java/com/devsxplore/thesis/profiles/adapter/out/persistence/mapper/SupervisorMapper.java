package com.devsxplore.thesis.profiles.adapter.out.persistence.mapper;

import com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity.SupervisorJDBCEntity;
import com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity.TopicJDBCEntity;
import com.devsxplore.thesis.profiles.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.devsxplore.thesis.profiles.domain.model.Contact.contactFromPrimitive;
import static com.devsxplore.thesis.profiles.domain.model.Name.nameFromPrimitive;
import static com.devsxplore.thesis.profiles.domain.model.Supervisor.createSupervisorWithId;

@Component
@RequiredArgsConstructor
public class SupervisorMapper {

    public Supervisor mapToDomainEntity(SupervisorJDBCEntity entity){

        var name = nameFromPrimitive(entity.firstName(), entity.lastName(), entity.title());

        var contact = contactFromPrimitive(entity.email(), entity.office(), entity.phone());

        var supervisor = createSupervisorWithId(
                new SupervisorId(entity.id()),
                name,
                contact
        );

        List<Topic> topics = entity.topics().stream().map(TopicMapper::mapToDomainEntity).toList();

        //FIXME: bei Controller kam nullpointer exception da ich auf die id der topics zugegriffen habe,allerdings wird hier die Id nicht mit übertragen. deswegen ist es immer null
        //FIXME: Deswegen habe ich in Supervisor neue Methode addTopicWithId hinzugefügt damit die ids mit rausgeladen werden wenn es zum domain objekt gemacht wird
        //FIXME: Falls es zu Problemen kommt supervisor.addTopicWithId zu addTopic machen
        topics.forEach(topic -> supervisor.addTopicWithId(topic.getId() ,topic.getTitle(), topic.getDescription()));

        return supervisor;
    }

    public SupervisorJDBCEntity toJDBCEntity(Supervisor supervisor) {
        List<TopicJDBCEntity> topicJDBCEntityList = supervisor.getTopics().stream()
                .map(TopicMapper::toJDBCEntity)
                .toList();

        return new SupervisorJDBCEntity(
                supervisor.getId(),
                supervisor.getAcademicTitle(),
                supervisor.getFirstName(),
                supervisor.getLastName(),
                supervisor.getEmailAsString(),
                supervisor.getOffice(),
                supervisor.getPhone(),
                topicJDBCEntityList
        );

    }
}
