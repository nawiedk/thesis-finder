package com.devsxplore.thesis.profiles.application.port.in.usecase.topic;

import com.devsxplore.thesis.profiles.application.port.in.command.topic.SaveChangesTopicCommand;
import com.devsxplore.thesis.profiles.domain.model.Topic;

public interface TopicSaveChangesUseCase {
    Topic saveChangesToTopic(SaveChangesTopicCommand command);
}
