package com.devsxplore.thesis.profiles.application.service;

import com.devsxplore.thesis.profiles.application.port.in.command.CreateSupervisorCommand;
import com.devsxplore.thesis.profiles.application.port.in.command.CreateTopicCommand;
import com.devsxplore.thesis.profiles.application.port.in.usecase.CreateSupervisorUseCase;
import com.devsxplore.thesis.profiles.application.port.in.usecase.CreateTopicUseCase;
import com.devsxplore.thesis.profiles.application.port.out.SupervisorRepositoryPort;
import com.devsxplore.thesis.profiles.domain.model.Supervisor;
import com.devsxplore.thesis.profiles.domain.model.Topic;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.devsxplore.thesis.profiles.domain.model.Supervisor.createSupervisorWithoutId;

@Service
@Transactional
@RequiredArgsConstructor
public class SupervisorService implements CreateSupervisorUseCase, CreateTopicUseCase {

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
        repositoryPort.save(supervisor);
        return repositoryPort.load(command.supervisorId())
                .getTopics()
                .stream()
                .filter(topic -> topic.getTitle().equals(command.title()))
                .findFirst()
                .orElseThrow();
    }
}
