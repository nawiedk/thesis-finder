package com.devsxplore.thesis.profiles.application.port.in.usecase;

import com.devsxplore.thesis.profiles.application.port.in.command.CreateTopicCommand;
import com.devsxplore.thesis.profiles.domain.model.Topic;

public interface CreateTopicUseCase {
    Topic createTopic(CreateTopicCommand command);
}
