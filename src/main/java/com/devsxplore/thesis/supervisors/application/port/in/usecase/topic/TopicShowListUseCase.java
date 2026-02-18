package com.devsxplore.thesis.supervisors.application.port.in.usecase.topic;

import com.devsxplore.thesis.supervisors.application.port.in.command.topic.ShowTopicListCommand;
import com.devsxplore.thesis.supervisors.domain.model.Topic;

import java.util.List;

public interface TopicShowListUseCase {

	public List<Topic> loadTopicsBySupervisor(ShowTopicListCommand command);

}
