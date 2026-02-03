package com.devsxplore.thesis.supervisors.domain.model;


//Tests entkoppeln
public class TopicBuilder {

    private Long id;
    private String title;
    private String description;

    public TopicBuilder addId(Long id){
        this.id = id;
        return this;
    }

    public TopicBuilder addTitle(String title){
        this.title = title;
        return this;
    }

    public TopicBuilder addDescription(String description){
        this.description = description;
        return this;
    }

    public Topic build(){
        return Topic.createTopicWithId(new TopicId(id), title, description);
    }
}
