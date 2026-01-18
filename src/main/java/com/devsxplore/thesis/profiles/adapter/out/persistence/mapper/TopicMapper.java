package com.devsxplore.thesis.profiles.adapter.out.persistence.mapper;

import com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity.TopicJDBCEntity;
import com.devsxplore.thesis.profiles.domain.model.Topic;
import com.devsxplore.thesis.profiles.domain.model.TopicId;

public class TopicMapper {

    public static Topic mapToDomainEntity(TopicJDBCEntity jdbcEntity){
        //TODO: nicht direkt auf Konstruktor von TopicId zugreifen.
        TopicId id = new TopicId(jdbcEntity.id());
        return Topic.createTopicWithId(id, jdbcEntity.topic(), jdbcEntity.description());
    }

    public static TopicJDBCEntity toJDBCEntity(Topic topic){
        return new TopicJDBCEntity(topic.getId(), topic.getTitle(), topic.getDescription());
    }

}
