package com.devsxplore.thesis.supervisors.application.port.in.command.topic;

public record ShowTopicListCommand(Long supervisorUserId) {
    public ShowTopicListCommand{
        if(supervisorUserId ==null || supervisorUserId <= 0){
            throw new IllegalArgumentException("Id must be positive");
        }
    }
}
