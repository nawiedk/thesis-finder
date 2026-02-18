package com.devsxplore.thesis.supervisors.application.port.in.usecase.topic;

import com.devsxplore.thesis.supervisors.application.port.in.command.topic.TopicDeleteCommand;

public interface TopicDeleteUseCase {

	boolean deleteTopic(TopicDeleteCommand command);

}
