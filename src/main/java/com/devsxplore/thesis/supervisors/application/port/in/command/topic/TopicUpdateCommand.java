package com.devsxplore.thesis.supervisors.application.port.in.command.topic;

public record TopicUpdateCommand(Long supervisorId, Long topicId, String title, String description) {
    public TopicUpdateCommand {
        if(supervisorId == null || supervisorId < 1){
            throw new IllegalArgumentException("Id must be positive");
        }
    }
}
