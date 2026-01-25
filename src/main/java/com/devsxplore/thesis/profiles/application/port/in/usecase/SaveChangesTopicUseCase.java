package com.devsxplore.thesis.profiles.application.port.in.usecase;

import com.devsxplore.thesis.profiles.application.port.in.command.SaveChangesTopicCommand;
import com.devsxplore.thesis.profiles.domain.model.Topic;

public interface SaveChangesTopicUseCase {
    Topic saveChangesToTopic(SaveChangesTopicCommand command);
}
