package com.devsxplore.thesis.profiles.application.port.in.usecase;

import com.devsxplore.thesis.profiles.application.port.in.command.ShowTopicListCommand;
import com.devsxplore.thesis.profiles.domain.model.Topic;

import java.util.List;

public interface ShowTopicListUseCase {
    public List<Topic> showTopicList(ShowTopicListCommand command);
}
