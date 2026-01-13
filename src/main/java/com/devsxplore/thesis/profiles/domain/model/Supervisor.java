package com.devsxplore.thesis.profiles.domain.model;

import java.util.ArrayList;
import java.util.List;

import static com.devsxplore.thesis.profiles.domain.model.Topic.createTopicWithoutId;

public class Supervisor {

    private final SupervisorId id;
    private final Name name;
    private final Contact contactDetails;
    private final List<FieldTag> fields;
    private final List<Link> links;
    private final List<InformationFile> files;
    private final List<Topic> topics;

    private Supervisor(SupervisorId id, Name name, Contact contactDetails) {
        if (name == null || contactDetails == null) {
            throw new IllegalArgumentException("Name and contact details cannot be null");
        }
        this.id = id;
        this.name = name;
        this.contactDetails = contactDetails;
        this.fields = new ArrayList<>();
        this.links = new ArrayList<>();
        this.files = new ArrayList<>();
        this.topics = new ArrayList<>();
    }

    public static Supervisor createSupervisorWithoutId(Name name, Contact contactDetails) {
        return new Supervisor(SupervisorId.unassigned(), name, contactDetails);
    }

    public static Supervisor createSupervisorWithId(SupervisorId id, Name name, Contact contactDetails) {
        return new Supervisor(id, name, contactDetails);
    }

    public SupervisorId getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public String getFullName() {
        return name.getFullName();
    }

    public Contact getContactDetails() {
        return contactDetails;
    }

    public List<FieldTag> getFields() {
        return List.copyOf(fields);
    }

    public List<Link> getLinks() {
        return List.copyOf(links);
    }

    public List<InformationFile> getFiles() {
        return List.copyOf(files);
    }

    public List<Topic> getTopics() {
        return List.copyOf(topics);
    }

    public boolean setFields(List<FieldTag> newFields) {
        if (newFields == null)
            throw new IllegalArgumentException("Fields cannot be null");
        fields.clear();
        return fields.addAll(List.copyOf(newFields));
    }

    public boolean addField(FieldTag newField) {
        if (newField == null)
            throw new IllegalArgumentException("Field cannot be null");
        return fields.add(newField);
    }

    public boolean addFields(List<FieldTag> newFields) {
        if (newFields == null)
            throw new IllegalArgumentException("Fields cannot be null");
        return fields.addAll(List.copyOf(newFields));
    }

    public boolean addTopic(String title, String description){
        Topic topic = createTopicWithoutId(title, description);
        return topics.add(topic);
    }

    public Long getTopicId(String title){
        for (Topic topic : topics){
            if(topic.getTitle().equals(title)){
                return topic.getId();
            }
        }

        return -1L;
    }

    public String getTopicIdByTitle(String title){
        for (Topic topic : topics){
            if(topic.getTitle().equals(title)){
                return topic.getDescription();
            }
        }

        return "";
    }

    public String getTitle(Long id){
        for(Topic topic : topics){
            if(topic.getId().equals(id)){
                return topic.getTitle();
            }
        }

        return "";
    }

    public String getDescription(Long id){
        for(Topic topic : topics){
            if(topic.getId().equals(id)){
                return topic.getDescription();
            }
        }

        return "";
    }


}