package com.devsxplore.thesis.profiles.domain.model;

import com.devsxplore.thesis.profiles.adapter.in.web.TopicNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.devsxplore.thesis.profiles.domain.model.Contact.contactFromPrimitive;
import static com.devsxplore.thesis.profiles.domain.model.Name.nameFromPrimitive;
import static com.devsxplore.thesis.profiles.domain.model.Topic.createTopicWithoutId;

@SuppressWarnings("LombokGetterMayBeUsed")
public class Supervisor {

    private final SupervisorId supervisorId;
    private Name name;
    private Contact contactDetails;
    private final Set<FieldTag> fields;
    private final Set<Topic> topics;

    private Supervisor(SupervisorId supervisorId, Name name, Contact contactDetails) {
        if (supervisorId == null) {
            throw new IllegalArgumentException("Supervisor ID cannot be null");
        }
        if (name == null || contactDetails == null) {
            throw new IllegalArgumentException("Name and contact details cannot be null");
        }
        this.supervisorId = supervisorId;
        this.name = name;
        this.contactDetails = contactDetails;
        fields = new HashSet<>();
        topics = new HashSet<>();
    }

    public static Supervisor createSupervisorWithoutId(Name name, Contact contactDetails) {
        return new Supervisor(SupervisorId.unassigned(), name, contactDetails);
    }

    public static Supervisor createSupervisorWithId(SupervisorId id, Name name, Contact contactDetails) {
        return new Supervisor(id, name, contactDetails);
    }

    public Long getSupervisorId() {
        return supervisorId.supervisorId();
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

    public String getAcademicTitle() {
        return name.title().getAbbreviation();
    }

    public String getEmailAsString() {
        return contactDetails.email().email();
    }

    public String getPhone() {
        return contactDetails.phone();
    }

    public String getOffice() {
        return contactDetails.office();
    }

    public void updateProfile(String firstName, String lastName, String title,
                              String email, String office, String phone) {
        this.name = nameFromPrimitive(
                (firstName == null || firstName.isBlank()) ? name.firstName() : firstName,
                (lastName == null || lastName.isBlank()) ? name.lastName() : lastName,
                (title == null || title.isBlank()) ? name.title().getAbbreviation() : title
        );
        this.contactDetails = contactFromPrimitive(
                (email == null || email.isBlank()) ? contactDetails.email().email() : email,
                (office == null || office.isBlank()) ? contactDetails.office() : office,
                (phone == null || phone.isBlank()) ? contactDetails.phone() : phone
        );
    }

    public Set<FieldTag> getFields() {
        return Set.copyOf(fields);
    }

    public Set<Topic> getTopics() {
        return Set.copyOf(topics);
    }

    private boolean containsField(String fieldName) {
        return fields.stream()
                .anyMatch(field -> field.fieldName().trim().equalsIgnoreCase(fieldName.trim()));
    }

    public void addField(String fieldName) {
        if (fieldName != null && !fieldName.isBlank() && !containsField(fieldName))
            fields.add(new FieldTag(fieldName));
    }

    public void addFields(Set<String> fieldNames) {
        if (fieldNames != null)
            for (String fieldName : fieldNames)
                this.addField(fieldName);
    }

    public boolean removeField(String fieldName) {
        if (fieldName == null || fieldName.isBlank())
            return false;
        return fields.removeIf(field -> field.fieldName().trim().equalsIgnoreCase(fieldName.trim()));
    }

    public void addExistingFields(FieldTag field) {
        this.fields.add(field);
    }

    public Topic addTopic(String title, String description) {
        Topic topic = createTopicWithoutId(title, description);
        topics.add(topic);
        return topic;
    }

    public Topic updateTopic(Long topicId, String newTitle, String newDescription) {
        Topic topic = findTopicByTopicId(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found"));
        return topic.updateTopic(newTitle, newDescription);
    }

    public boolean removeTopic(Long topicId) {
        return topics.removeIf(topic -> topic.getTopicId() != null && topic.getTopicId().equals(topicId));
    }

    private Optional<Topic> findTopicByTopicId(Long topicId) {
        return topics.stream()
                .filter(topic -> topic.getTopicId() != null && topic.getTopicId().equals(topicId))
                .findFirst();
    }

    public void addExistingTopic(Topic topic) {
        topics.add(topic);
    }

    public Topic addLinksToATopic(Long topicId, Set<String> links){
        Topic loadedTopic = topics.stream()
                .filter(topic -> topic.getTopicId().equals(topicId))
                .findFirst()
                .orElseThrow(() -> new TopicNotFoundException("Topic was not found with Id: " + topicId));

        Set<Link> linksObjects = links.stream()
                        .map(Link::new)
                                .collect(Collectors.toSet());

        loadedTopic.addLinksToTopic(linksObjects);
        return loadedTopic;
    }

    public Topic addFieldToATopic(Long topicId, Set<String> fields){
        Topic loadedTopic = topics.stream()
                .filter(topic -> topic.getTopicId().equals(topicId))
                .findFirst()
                .orElseThrow(() -> new TopicNotFoundException("Topic was not found with Id: " + topicId));

        Set<FieldTag> fieldObjects = fields.stream()
                        .map(FieldTag::new)
                                .collect(Collectors.toSet());

        loadedTopic.addFieldToTopic(fieldObjects);
        return loadedTopic;
    }
}