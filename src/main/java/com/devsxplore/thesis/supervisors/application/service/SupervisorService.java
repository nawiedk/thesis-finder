package com.devsxplore.thesis.supervisors.application.service;

import static com.devsxplore.thesis.supervisors.domain.model.Supervisor.createSupervisorWithoutId;

import java.util.List;
import java.util.Set;
import com.devsxplore.thesis.accounts.application.port.in.command.AssignUserRoleCommand;
import com.devsxplore.thesis.accounts.application.port.in.usecase.AssignUserRoleUseCase;
import com.devsxplore.thesis.accounts.domain.model.UserRole;
import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.*;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.CreateTopicCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.ShowTopicListCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.TopicDeleteCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.TopicUpdateCommand;
import com.devsxplore.thesis.supervisors.application.port.in.usecase.supervisor.*;
import com.devsxplore.thesis.supervisors.application.port.in.usecase.topic.*;
import com.devsxplore.thesis.supervisors.application.port.out.SupervisorRepositoryPort;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;
import com.devsxplore.thesis.supervisors.domain.model.Topic;
import com.devsxplore.thesis.supervisors.domain.model.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SupervisorService implements SupervisorCreateUseCase, TopicCreateUseCase, TopicShowListUseCase,
		TopicUpdateUseCase, TopicLoadUseCase, SupervisorUpdateUseCase, TopicDeleteUseCase, SupervisorShowAllUseCase,
		SupervisorDeleteUseCase, SupervisorLoadUseCase, FieldAddUseCase, LoadByUserIdUseCase, LoadByPublicIdUseCase {

	private final SupervisorRepositoryPort supervisorRepositoryPort;

	private final AssignUserRoleUseCase assignUserRoleUseCase;

	@Override
	public Supervisor createSupervisor(SupervisorCreateCommand command) {
		if (this.supervisorRepositoryPort.existsBySupervisorUserId(command.supervisorUserId())) {
			throw new IllegalArgumentException("Du bist bereits registriert");
		}

		Supervisor supervisor = createSupervisorWithoutId(new UserId(command.supervisorUserId()), command.name(),
				command.contactDetails());
		Supervisor saved = this.supervisorRepositoryPort.save(supervisor);
		this.assignUserRoleUseCase.assignRole(new AssignUserRoleCommand(command.supervisorUserId(), UserRole.SUPERVISOR));
		return saved;
	}

	@Override
	public Topic createTopic(CreateTopicCommand command) {
		Supervisor supervisor = this.supervisorRepositoryPort.load(command.supervisorUserId())
				.orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));

		supervisor.addTopic(command.title(), command.description());
		return this.supervisorRepositoryPort.save(supervisor)
				.getTopics()
				.stream()
				.filter(topic -> topic.getTitle().equals(command.title()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Topic not found"));
	}

	@Override
	public List<Topic> loadTopicsBySupervisor(ShowTopicListCommand command) {
		Supervisor supervisor = this.supervisorRepositoryPort.load(command.supervisorUserId())
				.orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));

		return List.copyOf(supervisor.getTopics());
	}

	@Override
	public Topic updateTopic(TopicUpdateCommand command) {
		Supervisor supervisor = this.supervisorRepositoryPort.load(command.supervisorUserId())
				.orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));

		Topic topic = supervisor.updateTopic(command.topicId(), command.title(), command.description());
		this.supervisorRepositoryPort.save(supervisor);

		return topic;
	}

	@Override
	public List<Topic> loadAllTopics() {
		return this.supervisorRepositoryPort.loadAll()
				.stream()
				.flatMap(supervisor -> supervisor.getTopics().stream())
				.toList();
	}

	@Override
	public Supervisor update(SupervisorUpdateCommand command) {
		Supervisor supervisor = this.supervisorRepositoryPort.loadByUserId(command.userId())
				.orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));
		supervisor.updateProfile(command.firstName(), command.lastName(), command.title(), command.email(),
				command.office(), command.phone());
		return this.supervisorRepositoryPort.save(supervisor);
	}

	@Override
	public boolean deleteTopic(TopicDeleteCommand command) {
		Supervisor supervisor = this.supervisorRepositoryPort.loadAll()
				.stream()
				.filter(s -> s.getTopics().stream().anyMatch(t -> t.getTopicId().equals(command.topicId())))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Topic not found"));

		boolean removed = supervisor.removeTopic(command.topicId());

		if (removed) {
			this.supervisorRepositoryPort.save(supervisor);
		}
		return removed;
	}

	@Override
	public Set<Supervisor> showAllSupervisors() {
		return Set.copyOf(this.supervisorRepositoryPort.loadAll());
	}

	@Override
	public boolean delete(SupervisorDeleteCommand command) {
		return this.supervisorRepositoryPort.delete(command.supervisorUserId());
	}

	@Override
	public Supervisor loadSupervisorById(SupervisorLoadCommand command) {
		return this.supervisorRepositoryPort.load(command.supervisorUserId())
				.orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));
	}

	@Override
	public void addFieldToSupervisor(FieldAddCommand command) {
		Supervisor supervisor = this.supervisorRepositoryPort.load(command.supervisorUserId())
				.orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));

		supervisor.addField(command.fieldName());
		this.supervisorRepositoryPort.save(supervisor);
	}

	@Override
	public Supervisor loadByUserId(LoadByUserIdCommand command) {
		return this.supervisorRepositoryPort.loadByUserId(command.userId())
				.orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));
	}

	@Override
	public Supervisor load(LoadByPublicIdCommand command) {
		return this.supervisorRepositoryPort.loadByPublicId(command.publicId())
				.orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));
	}
}
