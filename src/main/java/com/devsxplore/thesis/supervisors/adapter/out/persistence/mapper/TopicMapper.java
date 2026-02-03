package com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper;

import com.devsxplore.thesis.supervisors.adapter.out.persistence.jdbcentity.TopicJDBCEntity;
import com.devsxplore.thesis.supervisors.domain.model.Topic;
import com.devsxplore.thesis.supervisors.domain.model.TopicId;

import java.util.Set;
import java.util.stream.Collectors;

import static com.devsxplore.thesis.supervisors.domain.model.Topic.createTopicWithId;

public class TopicMapper {

    public static Topic mapTopicToDomainEntity(TopicJDBCEntity entity) {
        return createTopicWithId(
                new TopicId(entity.id()),
                entity.title(),
                entity.description());
    }

    public static TopicJDBCEntity mapTopicToJDBCEntity(Topic entity) {
        return new TopicJDBCEntity(
                entity.getTopicId(),
                entity.getTitle(),
                entity.getDescription()
        );
    }

    public static Set<Topic> mapTopicsToDomainEntities(Set<TopicJDBCEntity> entities) {
        if (entities == null)
            return Set.of();
        return entities.stream()
                .map(TopicMapper::mapTopicToDomainEntity)
                .collect(Collectors.toSet());
    }

    public static Set<TopicJDBCEntity> mapTopicsToJDBCEntities(Set<Topic> entities) {
        if (entities == null)
            return Set.of();
        return entities.stream()
                .map(TopicMapper::mapTopicToJDBCEntity)
                .collect(Collectors.toSet());
    }

}
