package com.devsxplore.thesis.profiles.application.port.in.command;

public record EditTopicCommand(Long supervisorId, Long topicId) {
    public EditTopicCommand{
        if(supervisorId == null || supervisorId < 1){
            throw new IllegalArgumentException("Id must be positive");
        }
    }
}
