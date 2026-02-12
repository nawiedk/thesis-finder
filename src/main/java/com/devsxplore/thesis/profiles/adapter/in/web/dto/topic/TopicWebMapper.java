package com.devsxplore.thesis.profiles.adapter.in.web.dto.topic;

import com.devsxplore.thesis.profiles.domain.model.Topic;

import java.util.List;

public class TopicWebMapper {
    public static TopicShowDTO toTopicShowDTO(Topic topic){
        return new TopicShowDTO(topic.getTopicId(), topic.getTopicId(), topic.getTitle(), topic.getDescription());
    }

    public static TopicSaveChangesDTO toSaveChangesDTO(Topic topic, Long supervisorId){
        return new TopicSaveChangesDTO(supervisorId, topic.getTopicId(), topic.getTitle(), topic.getDescription());
    }

    public static List<TopicShowDTO> toTopicShowDTOList(List<Topic> topics){
        return topics.stream()
                .map(TopicWebMapper::toTopicShowDTO)
                .toList();
    }

}
