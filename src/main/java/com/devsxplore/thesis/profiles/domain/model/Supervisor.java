package com.devsxplore.thesis.profiles.domain.model;

import com.devsxplore.thesis.profiles.adapter.out.persistence.adapterimpl.SupervisorPersistenceAdapter;
import com.devsxplore.thesis.profiles.application.port.in.command.CreateTopicCommand;
import com.devsxplore.thesis.profiles.application.service.SupervisorService;

import java.util.ArrayList;
import java.util.List;

import static com.devsxplore.thesis.profiles.domain.model.Topic.createTopicWithId;
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

    public Long getId() {
        return id.id();
    }

    public String getFullName() {
        return name.getFullName();
    }

    public String getFirstName() {
        return name.firstName();
    }

    public String getLastName() {
        return name.lastName();
    }

    public AcademicTitle getTitle() {
        return name.title();
    }

    public String getTitleAsString() {
        return name.title().toString();
    }

    public String getEmailAsString(){
        return contactDetails.email().email();
    }

    public String getPhone(){
        return contactDetails.phone();
    }

    public String getOffice(){
        return contactDetails.office();
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

    public boolean addTopicWithId(Long id, String title, String description){
        Topic topic = createTopicWithId(new TopicId(id), title, description);
        return topics.add(topic);
    }

    public Topic editTopic(Long id, String title, String description){
        for (Topic topic : topics){
            if(topic.getId().equals(id)){
                topic.updateTopic(title, description);
                return topic;
            }
        }
        return null;
    }

    public Long getTopicId(String title){
        for (Topic topic : topics){
            if(topic.getTitle().equals(title)){
                return topic.getId();
            }
        }

        return -1L;
    }

    public List<Topic> getTopicIdByTitle(String title){
        return topics.stream()
                .filter(topic -> topic.getTitle().equals(title))
                .toList();
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