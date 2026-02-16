package com.devsxplore.thesis.supervisors.application.service;

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

import java.util.List;

import static com.devsxplore.thesis.supervisors.domain.model.Supervisor.createSupervisorWithoutId;

@Service
@Transactional
@RequiredArgsConstructor
public class SupervisorService implements
        SupervisorCreateUseCase,
        TopicCreateUseCase,
        TopicShowListUseCase,
        TopicUpdateUseCase,
        TopicLoadUseCase,
        SupervisorUpdateUseCase,
        TopicDeleteUseCase,
        SupervisorShowAllUseCase,
        SupervisorDeleteUseCase,
        SupervisorLoadUseCase,
        FieldAddUseCase,
        LoadByUserIdUseCase {

    private final SupervisorRepositoryPort supervisorRepositoryPort;
    private final AssignUserRoleUseCase assignUserRoleUseCase;

    @Override
    public Supervisor createSupervisor(SupervisorCreateCommand command) {
        if (supervisorRepositoryPort.existsBySupervisorUserId(command.supervisorUserId()))
            throw new IllegalArgumentException("Du bist bereits registriert");

        Supervisor supervisor = createSupervisorWithoutId(
                new UserId(command.supervisorUserId()),
                command.name(),
                command.contactDetails()
        );
        Supervisor saved = supervisorRepositoryPort.save(supervisor);
        assignUserRoleUseCase.assignRole(new AssignUserRoleCommand(command.supervisorUserId(), UserRole.SUPERVISOR));
        return saved;
    }

    @Override
    public Topic createTopic(CreateTopicCommand command) {
        Supervisor supervisor = supervisorRepositoryPort.load(command.supervisorUserId())
                .orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));

        supervisor.addTopic(command.title(), command.description());
        return supervisorRepositoryPort.save(supervisor)
                .getTopics()
                .stream()
                .filter(topic -> topic.getTitle().equals(command.title()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Topic not found"));
    }

    @Override
    public List<Topic> loadTopicsBySupervisor(ShowTopicListCommand command) {
        Supervisor supervisor = supervisorRepositoryPort.load(command.supervisorUserId())
                .orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));

        return List.copyOf(supervisor.getTopics());
    }

    @Override
    public Topic updateTopic(TopicUpdateCommand command) {
        Supervisor supervisor = supervisorRepositoryPort.load(command.supervisorUserId())
                .orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));

        Topic topic = supervisor.updateTopic(command.topicId(), command.title(), command.description());
        supervisorRepositoryPort.save(supervisor);

        return topic;
    }

    @Override
    public List<Topic> loadAllTopics() {
        return supervisorRepositoryPort.loadAll().stream()
                .flatMap(supervisor -> supervisor.getTopics().stream())
                .toList();
    }

    @Override
    public Supervisor updateSupervisorProfile(SupervisorUpdateCommand command) {
        Supervisor supervisor = supervisorRepositoryPort.load(command.supervisorUserId())
                .orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));
        supervisor.updateProfile(command.firstName(), command.lastName(), command.title(), command.email(), command.office(), command.phone());
        return supervisorRepositoryPort.save(supervisor);
    }

    @Override
    public boolean deleteTopic(TopicDeleteCommand command) {
        Supervisor supervisor = supervisorRepositoryPort.loadAll().stream()
                .filter(s -> s.getTopics().stream()
                        .anyMatch(t -> t.getTopicId().equals(command.topicId())))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Topic not found"));

        boolean removed = supervisor.removeTopic(command.topicId());

        if (removed) {
            supervisorRepositoryPort.save(supervisor);
        }
        return removed;
    }

    @Override
    public List<Supervisor> showAllSupervisors() {
        return List.copyOf(
                supervisorRepositoryPort.loadAll()
        );
    }

    @Override
    public boolean deleteSupervisor(SupervisorDeleteCommand command) {
        return supervisorRepositoryPort.delete(command.supervisorUserId());
    }

    @Override
    public Supervisor loadSupervisorById(SupervisorLoadCommand command) {
        return supervisorRepositoryPort.load(command.supervisorUserId())
                .orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));
    }

    @Override
    public void addFieldToSupervisor(FieldAddCommand command) {
        Supervisor supervisor = supervisorRepositoryPort.load(command.supervisorUserId())
                .orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));

        supervisor.addField(command.fieldName());
        supervisorRepositoryPort.save(supervisor);
    }

    @Override
    public Supervisor loadSupervisor(LoadByUserIdCommand command) {
        return supervisorRepositoryPort.loadByUserId(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));
    }
}
