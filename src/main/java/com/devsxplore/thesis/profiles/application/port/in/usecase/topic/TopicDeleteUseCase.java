package com.devsxplore.thesis.profiles.application.port.in.usecase.topic;

import com.devsxplore.thesis.profiles.application.port.in.command.topic.TopicDeleteCommand;

public interface TopicDeleteUseCase {
    boolean deleteTopic(TopicDeleteCommand command);
}
