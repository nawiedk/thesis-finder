package com.devsxplore.thesis.profiles.domain.model;


import com.devsxplore.thesis.profiles.adapter.out.persistence.adapterimpl.SupervisorPersistenceAdapter;
import com.devsxplore.thesis.profiles.application.port.in.command.CreateTopicCommand;
import com.devsxplore.thesis.profiles.application.port.in.command.EditTopicCommand;
import com.devsxplore.thesis.profiles.application.port.in.command.SaveChangesTopicCommand;
import com.devsxplore.thesis.profiles.application.port.in.command.ShowTopicListCommand;
import com.devsxplore.thesis.profiles.application.port.out.SupervisorRepositoryPort;
import com.devsxplore.thesis.profiles.application.service.SupervisorService;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest;

import java.util.List;

import static com.devsxplore.thesis.profiles.domain.model.SupervisorFactory.createSupervisorWithNoTopics;
import static com.devsxplore.thesis.profiles.domain.model.SupervisorFactory.createSupervisorWithTopics;
import static com.devsxplore.thesis.profiles.domain.model.TopicFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SupervisorServiceTest {


    //TODO: WICHTIG SUPERVISOR BUILDER UND FACTORY SCHREIBEN ANSONSTEN GEHEN TESTS KAPUTT FALLS AGGREGAT GEÄNDERT WIRD !!!!

    SupervisorRepositoryPort repositoryPort = mock(SupervisorRepositoryPort.class);


    @Test
    @DisplayName("If Supervisor with x amount of Topics is added to database service.showTopicList contains x amount of topics")
    void test_1() {
        Supervisor supervisor = createSupervisorWithNoTopics();
        for(Topic topic : createBatFamily()){
            supervisor.addTopic(topic.getTitle(), topic.getDescription());
        }
        when(repositoryPort.load(1L)).thenReturn(supervisor);
        SupervisorService service = new SupervisorService(repositoryPort);
        ShowTopicListCommand command = new ShowTopicListCommand(1L);

        List<Topic> topics = service.showTopicList(command);

        assertThat(topics.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("editTopic method returns correct Topic attributes")
    void test_2() {
        Supervisor supervisor = createSupervisorWithTopics();
        when(repositoryPort.load(1L)).thenReturn(supervisor);
        SupervisorService service = new SupervisorService(repositoryPort);
        EditTopicCommand command = new EditTopicCommand(1L,1L);

        Topic topic = service.editTopic(command);

        assertThat(topic.getTitle()).isEqualTo("Thema1");
        assertThat(topic.getDescription()).isEqualTo("Beschreibung1");
    }

    @Test
    @DisplayName("editTopic method invokes load method of adapter correctly")
    void test_3() {
        Supervisor supervisor = createSupervisorWithTopics();
        when(repositoryPort.load(1L)).thenReturn(supervisor);
        SupervisorService service = new SupervisorService(repositoryPort);
        EditTopicCommand command = new EditTopicCommand(1L,1L);

        service.editTopic(command);

        verify(repositoryPort).load(1L);
    }

    @Test
    @DisplayName("saveChangesToTopic correctly invokes port.load() with right parameters")
    void test_4() {
        when(repositoryPort.load(any())).thenReturn(createSupervisorWithTopics());
        SaveChangesTopicCommand command = new SaveChangesTopicCommand(1L, 1L, "Bruce Wayne", "Batman");
        SupervisorService service = new SupervisorService(repositoryPort);

        service.saveChangesToTopic(command);

        verify(repositoryPort).load(1L);
    }

    @Test
    @DisplayName("saveChangesToTopic correctly saves same supervisor that has been loaded")
    void test_5() {
        Supervisor supervisor = createSupervisorWithTopics();
        when(repositoryPort.load(any())).thenReturn(supervisor);
        SupervisorService service = new SupervisorService(repositoryPort);
        SaveChangesTopicCommand command = new SaveChangesTopicCommand(1L, 1L, "Bruce Wayne", "Batman");

        service.saveChangesToTopic(command);

        verify(repositoryPort).save(supervisor);
    }

    @Test
    @DisplayName("saveChangesToTopic correctly returns Topic with same Information if information is not changed")
    void test_6() {
        Supervisor supervisor = createSupervisorWithTopics();
        when(repositoryPort.load(1L)).thenReturn(supervisor);
        SaveChangesTopicCommand command = new SaveChangesTopicCommand(1L, 1L, "Bruce Wayne", "Batman");
        SupervisorService service = new SupervisorService(repositoryPort);

        Topic topic = service.saveChangesToTopic(command);

        assertThat(topic.getTitle()).isEqualTo("Bruce Wayne");
        assertThat(topic.getDescription()).isEqualTo("Batman");
    }

    @Test
    @DisplayName("saveChangesToTopic correctly returns Topic with same Information if information is not changed")
    void test_7() {
        Supervisor supervisor = createSupervisorWithTopics();
        when(repositoryPort.load(1L)).thenReturn(supervisor);
        SaveChangesTopicCommand command = new SaveChangesTopicCommand(1L, 1L, "Anakin Skywalker", "Jedi");
        SupervisorService service = new SupervisorService(repositoryPort);

        Topic topic = service.saveChangesToTopic(command);

        assertThat(topic.getTitle()).isNotEqualTo("Bruce Wayne");
        assertThat(topic.getDescription()).isNotEqualTo("Batman");

        assertThat(topic.getTitle()).isEqualTo("Anakin Skywalker");
        assertThat(topic.getDescription()).isEqualTo("Jedi");
    }

    @Test
    @DisplayName("If createTopic is invoked port.load ist invoked with correct supervisorId")
    void test_8() {
        Supervisor supervisor = createSupervisorWithNoTopics();
        when(repositoryPort.load(any())).thenReturn(supervisor);
        when(repositoryPort.save(any())).thenReturn(supervisor);
        SupervisorService service = new SupervisorService(repositoryPort);
        CreateTopicCommand command = new CreateTopicCommand(1L, "Bruce Wayne", "Batman");

        service.createTopic(command);

        verify(repositoryPort).load(1L);
    }

    @Test
    @DisplayName("If createTopic is invoked Topic object with correct attributes is returned/Supervisor topic list contains correct object")
    void test_9() {
        Supervisor supervisor = createSupervisorWithNoTopics();
        when(repositoryPort.load(any())).thenReturn(supervisor);
        when(repositoryPort.save(any())).thenReturn(supervisor);
        SupervisorService service = new SupervisorService(repositoryPort);
        CreateTopicCommand command = new CreateTopicCommand(1L, "Bruce Wayne", "Batman");

        Topic topic = service.createTopic(command);

        assertThat(topic.getTitle()).isEqualTo("Bruce Wayne");
        assertThat(supervisor.getTopics().getFirst().getTitle()).isEqualTo("Bruce Wayne");
    }


}
