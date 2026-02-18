package com.devsxplore.thesis.supervisors.application.port.in.usecase.topic;

import com.devsxplore.thesis.supervisors.application.port.in.command.topic.TopicUpdateCommand;
import com.devsxplore.thesis.supervisors.domain.model.Topic;

public interface TopicUpdateUseCase {

	Topic updateTopic(TopicUpdateCommand command);

}
