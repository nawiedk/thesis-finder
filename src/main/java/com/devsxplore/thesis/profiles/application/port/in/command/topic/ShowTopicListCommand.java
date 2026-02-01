package com.devsxplore.thesis.profiles.application.port.in.command.topic;

public record ShowTopicListCommand(Long supervisorId) {
    public ShowTopicListCommand{
        if(supervisorId==null || supervisorId <= 0){
            throw new IllegalArgumentException("Id must be positive");
        }
    }
}
