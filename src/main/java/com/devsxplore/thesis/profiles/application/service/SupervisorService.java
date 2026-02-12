package com.devsxplore.thesis.profiles.application.service;

import com.devsxplore.thesis.profiles.adapter.in.web.TopicNotFoundException;
import com.devsxplore.thesis.profiles.adapter.out.persistence.repository.SupervisorRepository;
import com.devsxplore.thesis.profiles.application.port.in.command.supervisor.*;
import com.devsxplore.thesis.profiles.application.port.in.command.topic.*;
import com.devsxplore.thesis.profiles.application.port.in.usecase.supervisor.*;
import com.devsxplore.thesis.profiles.application.port.in.usecase.topic.*;
import com.devsxplore.thesis.profiles.application.port.out.SupervisorRepositoryPort;
import com.devsxplore.thesis.profiles.domain.model.Supervisor;
import com.devsxplore.thesis.profiles.domain.model.Topic;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.devsxplore.thesis.profiles.domain.model.Supervisor.createSupervisorWithoutId;

@Service
@Transactional
@RequiredArgsConstructor
public class SupervisorService implements
        SupervisorCreateUseCase,
        TopicCreateUseCase,
        TopicShowListUseCase,
        TopicUpdateUseCase,
        TopicSaveChangesUseCase,
        TopicLoadUseCase,
        SupervisorUpdateUseCase,
        TopicDeleteUseCase,
        SupervisorShowAllUseCase,
        SupervisorDeleteUseCase,
        SupervisorLoadUseCase,
        FieldAddUseCase
{

    private final SupervisorRepositoryPort supervisorRepositoryPort;

    @Override
    public Supervisor createSupervisor(SupervisorCreateCommand command) {
        Supervisor supervisor = createSupervisorWithoutId(
                command.name(),
                command.contactDetails()
        );
        return supervisorRepositoryPort.save(supervisor);
    }

    @Override
    public Topic createTopic(CreateTopicCommand command) {
        Supervisor supervisor = supervisorRepositoryPort.load(command.supervisorId())
                .orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));

        Topic newTopic = supervisor.addTopic(command.title(), command.description());
        supervisorRepositoryPort.save(supervisor);
        return newTopic;
        //Modifikation damit fields und links hinzugefügt werden können


    }

    @Override
    public List<Topic> loadTopicsBySupervisor(ShowTopicListCommand command) {
        Supervisor supervisor = supervisorRepositoryPort.load(command.supervisorId())
                .orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));

        return List.copyOf(supervisor.getTopics());
    }

    @Override
    public Topic updateTopic(TopicUpdateCommand command) {
        Supervisor supervisor = supervisorRepositoryPort.load(command.supervisorId())
                .orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));

        Topic topic = supervisor.updateTopic(command.topicId(), command.title(), command.description());
        supervisorRepositoryPort.save(supervisor);

        return topic;
    }

    @Override
    public Topic saveChangesToTopic(SaveChangesTopicCommand command) {
        Supervisor supervisor = supervisorRepositoryPort.load(command.supervisorId())
                .orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));

        supervisor.updateTopic(command.topicId(), command.title(), command.description());
        supervisorRepositoryPort.save(supervisor);

        return supervisor.getTopics().stream()
                .filter(t -> t.getTopicId().equals(command.topicId()))
                .findFirst()
                .orElseThrow(() -> new TopicNotFoundException("Thema mit Id '" + command.topicId() + "' konnte nicht bearbeitet werden."));
    }

    @Override
    public List<Topic> loadAllTopics() {
        return supervisorRepositoryPort.loadAll().stream()
                .flatMap(supervisor -> supervisor.getTopics().stream())
                .toList();
    }

    @Override
    public Topic loadSingleTopicById(LoadSingleTopicCommand command) {
        Supervisor supervisor = supervisorRepositoryPort.load(command.supervisorId()).orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));
        return supervisor.getTopics().stream()
                .filter(topic -> topic.getTopicId().equals(command.topicId()))
                .findFirst()
                .orElseThrow(() -> new TopicNotFoundException("Topic mit Id '" + command.topicId() + "' konnte nicht gefunden werden."));
    }

    @Override
    public Supervisor updateSupervisorProfile(SupervisorUpdateCommand command) {
        Supervisor supervisor = supervisorRepositoryPort.load(command.supervisorId())
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
                .orElseThrow(() -> new TopicNotFoundException("Thema mit Id '" + command.topicId() + "' konnte nicht gelöscht werden."));

        boolean removed = supervisor.removeTopic(command.topicId());

        if (removed)
            supervisorRepositoryPort.save(supervisor);

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
        return supervisorRepositoryPort.delete(command.supervisorId());
    }

    @Override
    public Supervisor loadSupervisorById(SupervisorLoadCommand command) {
        return supervisorRepositoryPort.load(command.supervisorId())
                .orElseThrow((() -> new IllegalArgumentException("Supervisor not found")));
    }

    @Override
    public void addFieldToSupervisor(FieldAddCommand command) {
        Supervisor supervisor = supervisorRepositoryPort.load(command.supervisorId())
                .orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));

        supervisor.addField(command.fieldName());
        supervisorRepositoryPort.save(supervisor);
    }
}
