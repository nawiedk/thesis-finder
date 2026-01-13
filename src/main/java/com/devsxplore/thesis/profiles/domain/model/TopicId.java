package com.devsxplore.thesis.profiles.domain.model;

public record TopicId(Long id) {
    public TopicId{
        if(id != null && id <= 0){
            throw new IllegalArgumentException("Id must be positive");
        }
    }

    public static TopicId unassigned(){
        return new TopicId(null);
    }
}
