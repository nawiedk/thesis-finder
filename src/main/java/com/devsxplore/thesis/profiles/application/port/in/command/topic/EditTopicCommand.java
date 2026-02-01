package com.devsxplore.thesis.profiles.application.port.in.command.topic;

public record EditTopicCommand(Long supervisorId, Long topicId, String title, String description) {
    public EditTopicCommand{
        if(supervisorId == null || supervisorId < 1){
            throw new IllegalArgumentException("Id must be positive");
        }
    }
}
