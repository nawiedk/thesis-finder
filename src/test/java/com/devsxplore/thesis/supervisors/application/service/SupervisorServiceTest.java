package com.devsxplore.thesis.supervisors.application.service;

import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.SupervisorCreateCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.CreateTopicCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.ShowTopicListCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.TopicDeleteCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.TopicUpdateCommand;
import com.devsxplore.thesis.supervisors.application.port.out.SupervisorRepositoryPort;
import com.devsxplore.thesis.supervisors.domain.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.devsxplore.thesis.supervisors.domain.model.SupervisorFactory.createSupervisorWithNoTopics;
import static com.devsxplore.thesis.supervisors.domain.model.TopicFactory.createBatFamily;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SupervisorServiceTest {

	private final SupervisorRepositoryPort repositoryPort = mock(SupervisorRepositoryPort.class);

	private final SupervisorService service = new SupervisorService(repositoryPort, null);

	@Test
	@DisplayName("loadTopicsBySupervisor gibt alle Themen zurück")
	void should_return_all_topics_when_supervisor_exists() {
		Supervisor supervisor = createSupervisorWithNoTopics();
		for (Topic topic : createBatFamily()) {
			supervisor.addTopic(topic.getTitle(), topic.getDescription());
		}
		when(repositoryPort.load(1L)).thenReturn(Optional.of(supervisor));
		ShowTopicListCommand command = new ShowTopicListCommand(1L);

		List<Topic> topics = service.loadTopicsBySupervisor(command);

		assertThat(topics).hasSize(4);
	}

	@Test
	@DisplayName("updateTopic aktualisiert Titel und Beschreibung")
	void should_update_topic_title_and_description() {
		Supervisor supervisor = createSupervisorWithNoTopics();
		Topic topicWithId = Topic.createTopicWithId(new TopicId(100L), "Alter Titel", "Alte Desc");
		supervisor.addExistingTopic(topicWithId);
		when(repositoryPort.load(1L)).thenReturn(Optional.of(supervisor));
		TopicUpdateCommand command = new TopicUpdateCommand(1L, 100L, "Neuer Titel", "Neue Beschreibung");
		Topic updatedTopic = service.updateTopic(command);
		assertThat(updatedTopic.getTitle()).isEqualTo("Neuer Titel");
		verify(repositoryPort).save(supervisor);
	}

	@Test
	@DisplayName("createTopic speichert das Topic im Supervisor")
	void should_add_topic_to_supervisor_list() {
		Supervisor supervisor = createSupervisorWithNoTopics();
		when(repositoryPort.load(1L)).thenReturn(Optional.of(supervisor));
		when(repositoryPort.save(any(Supervisor.class))).thenAnswer(i -> i.getArgument(0));
		CreateTopicCommand command = new CreateTopicCommand(1L, "Bruce Wayne", "Batman");

		Topic topic = service.createTopic(command);

		assertThat(topic.getTitle()).isEqualTo("Bruce Wayne");
		assertThat(supervisor.getTopics()).extracting(Topic::getTitle).contains("Bruce Wayne");
		verify(repositoryPort).save(supervisor);
	}

	@Test
	void createSupervisor_ShouldThrowException_WhenUserAlreadyExists() {
		Long userId = 1L;
		SupervisorCreateCommand command = new SupervisorCreateCommand(userId, new Name("John", "Doe", null),
				Contact.contactFromPrimitive("john@hhu.de", "Room 1", "123"));

		when(repositoryPort.existsBySupervisorUserId(userId)).thenReturn(true);

		assertThatThrownBy(() -> service.createSupervisor(command)).isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Du bist bereits registriert");
	}

	@Test
	void deleteTopic_should_return_false_if_topic_not_found_in_list() {
		Supervisor supervisor = createSupervisorWithNoTopics();
		supervisor.addTopic("Titel", "Desc");

		when(repositoryPort.loadAll()).thenReturn(List.of(supervisor));

		assertThatThrownBy(() -> service.deleteTopic(new TopicDeleteCommand(999L)))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Topic not found");
	}

	@Test
	void updateTopic_should_not_change_values_if_null_passed() {
		Supervisor supervisor = createSupervisorWithNoTopics();
		Topic topic = supervisor.addTopic("Original", "Original Desc");

		when(repositoryPort.load(anyLong())).thenReturn(Optional.of(supervisor));

		topic.updateTopic(null, null);

		assertThat(topic.getTitle()).isEqualTo("Original");
		assertThat(topic.getDescription()).isEqualTo("Original Desc");
	}

}