package com.devsxplore.thesis.profiles.application.port.in.usecase;

import com.devsxplore.thesis.profiles.application.port.in.command.EditTopicCommand;
import com.devsxplore.thesis.profiles.domain.model.Topic;

public interface EditTopicUseCase {
    Topic editTopic(EditTopicCommand command);
}
