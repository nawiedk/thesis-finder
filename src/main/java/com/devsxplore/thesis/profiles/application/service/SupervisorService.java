package com.devsxplore.thesis.profiles.application.service;

import com.devsxplore.thesis.profiles.application.port.in.command.*;
import com.devsxplore.thesis.profiles.application.port.in.usecase.*;
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
public class SupervisorService implements CreateSupervisorUseCase, CreateTopicUseCase, ShowTopicListUseCase, EditTopicUseCase, SaveChangesTopicUseCase {

    private final SupervisorRepositoryPort repositoryPort;

    @Override
    public Supervisor createSupervisor(CreateSupervisorCommand command) {
        var supervisor = createSupervisorWithoutId(command.name(),
                command.contactDetails());
        return repositoryPort.save(supervisor);
    }

    @Override
    public Topic createTopic(CreateTopicCommand command) {
        Supervisor supervisor = repositoryPort.load(command.supervisorId());
        supervisor.addTopic(command.title(), command.description());
        return repositoryPort.save(supervisor)
                .getTopics()
                .stream()
                .filter(topic -> topic.getTitle().equals(command.title()))
                .findFirst()
                .orElseThrow();
    }


    @Override
    public List<Topic> showTopicList(ShowTopicListCommand command) {
        Supervisor supervisor = repositoryPort.load(command.supervisorId());
        return supervisor.getTopics();
    }

    @Override
    public Topic editTopic(EditTopicCommand command) {
        Supervisor supervisor = repositoryPort.load(command.supervisorId());
        return supervisor.getTopics().stream()
                .filter(topic -> topic.getId().equals(command.topicId()))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public Topic saveChangesToTopic(SaveChangesTopicCommand command) {
        Supervisor supervisor = repositoryPort.load(command.supervisorId());
        Topic topic = supervisor.editTopic(command.topicId(), command.title(), command.description());
        repositoryPort.save(supervisor);
        return topic;
    }
}
