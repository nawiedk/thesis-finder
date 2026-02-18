package com.devsxplore.thesis.supervisors.domain.model;

import java.util.*;

import static com.devsxplore.thesis.supervisors.domain.model.Contact.contactFromPrimitive;
import static com.devsxplore.thesis.supervisors.domain.model.Name.nameFromPrimitive;
import static com.devsxplore.thesis.supervisors.domain.model.Topic.createTopicWithoutId;

@SuppressWarnings("LombokGetterMayBeUsed")
public class Supervisor {

	private final SupervisorId supervisorId;

	private final UserId userId;

	private final PublicId publicId;

	private final Set<FieldTag> fields;

	private final Set<Topic> topics;

	private Name name;

	private Contact contactDetails;

	private Supervisor(SupervisorId supervisorId, UserId userId, PublicId publicId, Name name, Contact contactDetails) {
		this.supervisorId = Objects.requireNonNull(supervisorId, "Supervisor ID cannot be null");
		this.userId = Objects.requireNonNull(userId, "UserID cannot be null");
		this.publicId = Objects.requireNonNull(publicId, "Public ID cannot be null");
		this.name = Objects.requireNonNull(name, "Name cannot be null");
		this.contactDetails = Objects.requireNonNull(contactDetails, "Contact details cannot be null");
		fields = new HashSet<>();
		topics = new HashSet<>();
	}

	public static Supervisor createSupervisorWithoutId(UserId userId, Name name, Contact contactDetails) {
		return new Supervisor(SupervisorId.unassigned(), userId, PublicId.generate(), name, contactDetails);
	}

	public static Supervisor createSupervisorWithId(SupervisorId id, UserId userId, PublicId publicId, Name name,
			Contact contactDetails) {
		return new Supervisor(id, userId, publicId, name, contactDetails);
	}

	public UUID getPublicId() {
		return publicId.uuid();
	}

	public Long getSupervisorId() {
		return supervisorId.supervisorId();
	}

	public Long getUserID() {
		return userId.userId();
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

	public void updateProfile(String firstName, String lastName, String title, String email, String office,
			String phone) {
		this.name = nameFromPrimitive((firstName == null || firstName.isBlank()) ? name.firstName() : firstName,
				(lastName == null || lastName.isBlank()) ? name.lastName() : lastName,
				(title == null || title.isBlank()) ? name.title().getAbbreviation() : title);
		this.contactDetails = contactFromPrimitive(
				(email == null || email.isBlank()) ? contactDetails.email().email() : email,
				(office == null || office.isBlank()) ? contactDetails.office() : office,
				(phone == null || phone.isBlank()) ? contactDetails.phone() : phone);
	}

	public Set<FieldTag> getFields() {
		return Set.copyOf(fields);
	}

	public Set<Topic> getTopics() {
		return Set.copyOf(topics);
	}

	private boolean containsField(String fieldName) {
		if (fields.isEmpty())
			return false;
		return fields.stream().anyMatch(field -> field.fieldName().trim().equalsIgnoreCase(fieldName.trim()));
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
		Topic topic = findTopicByTopicId(topicId).orElseThrow(() -> new IllegalArgumentException("Topic not found"));
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

}