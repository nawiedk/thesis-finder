package com.devsxplore.thesis.profiles.domain.model;

import com.devsxplore.thesis.profiles.adapter.out.persistence.adapterimpl.SupervisorPersistenceAdapter;
import com.devsxplore.thesis.profiles.adapter.out.persistence.mapper.SupervisorMapper;
import com.devsxplore.thesis.profiles.adapter.out.persistence.mapper.TopicMapper;
import com.devsxplore.thesis.profiles.application.port.in.command.CreateTopicCommand;
import com.devsxplore.thesis.profiles.application.port.in.command.SaveChangesTopicCommand;
import com.devsxplore.thesis.profiles.application.port.in.command.ShowTopicListCommand;
import com.devsxplore.thesis.profiles.application.service.SupervisorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;
import org.springframework.test.context.jdbc.Sql;

import static com.devsxplore.thesis.profiles.domain.model.SupervisorFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import({SupervisorPersistenceAdapter.class, SupervisorMapper.class, SupervisorService.class})
@DataJdbcTest
public class SupervisorAdapterTest {

    @Autowired
    SupervisorPersistenceAdapter adapter;


    @Test
    @Sql("/com/devsxplore/thesis/profiles/domain/model/clear_data.sql")
    @DisplayName("When a supervisor is saved with id the supevisor you load with same id has the same attributes")
    void test_1() {
        Supervisor supervisor = createSupervisorWithNoTopicsAndNoId();
        Supervisor supervisorSaved = adapter.save(supervisor);

        Supervisor supervisorLoaded = adapter.load(supervisorSaved.getId());

        assertThat(supervisorLoaded.getFullName()).isEqualTo(supervisor.getFullName());
    }

    @Test
    @Sql("/com/devsxplore/thesis/profiles/domain/model/clear_data.sql")
    @DisplayName("When supervisor is saved with topics when loaded it has the same topics")
    void test_2() {
        Supervisor saved = adapter.save( createSupervisorWithTopicsAndNoId());
        Supervisor loaded = adapter.load(saved.getId());

        assertThat(loaded.getTopics().size()).isEqualTo(2);
    }

    @Test
    @Sql("/com/devsxplore/thesis/profiles/domain/model/clear_data.sql")
    @DisplayName("When supervisor topic is updated saved and loaded topic attributes are changed ")
    void test_3() {
        Supervisor saved = adapter.save( createSupervisorWithTopicsAndNoId());
        Supervisor loaded = adapter.load(saved.getId());
        loaded.editTopic(1L,"Anakin Skywalker", "Jedi");
        Supervisor savedAgain = adapter.save(loaded);
        Supervisor loadedAgain = adapter.load(saved.getId());

        assertThat(loadedAgain.getTopics().getFirst().getTitle()).isEqualTo("Anakin Skywalker");
        assertThat(saved.getTopics().getFirst().getTitle()).isEqualTo("Thema1");
    }

    //TODO Integrationstests zwischen service und adapter
    //TODO: ich hab verkackt da wenn ich Topic ändere auf weitere Attribute muss ich alle benutzten commands in den tests abändern </3
    @Test
    @Sql("/com/devsxplore/thesis/profiles/domain/model/clear_data.sql")
    @DisplayName("createTopic correctly saves a topic in database")
    void test_4() {
        SupervisorService service = new SupervisorService(adapter);
        Supervisor supervisor = adapter.save(createSupervisorWithNoTopicsAndNoId());
        CreateTopicCommand command = new CreateTopicCommand(supervisor.getId(),"Bruce Wayne", "Batman");
        Topic topic = service.createTopic(command);

        assertThat(topic.getTitle()).isEqualTo("Bruce Wayne");
    }

    @Test
    @Sql("/com/devsxplore/thesis/profiles/domain/model/clear_data.sql")
    @DisplayName("showTopicList correctly Loads topics from database")
    void test_5() {
        Supervisor supervisor = adapter.save(createSupervisorWithTopicsAndNoId());

        SupervisorService service = new SupervisorService(adapter);
        ShowTopicListCommand command = new ShowTopicListCommand(supervisor.getId());

        assertThat(service.showTopicList(command)).hasSize(2);
    }

    @Test
    @Sql("/com/devsxplore/thesis/profiles/domain/model/clear_data.sql")
    @DisplayName("showTopicList correctly Loads topics from database")
    void test_6() {
        Supervisor supervisor = adapter.save(createSupervisorWithTopicsAndNoId());

        SupervisorService service = new SupervisorService(adapter);
        SaveChangesTopicCommand command = new SaveChangesTopicCommand(supervisor.getId(), 1L, "Anakin Skywalker", "Jedi");

        Topic topic = service.saveChangesToTopic(command);

        assertThat(topic.getTitle()).isEqualTo("Anakin Skywalker");

        Supervisor loaded = adapter.load(supervisor.getId());
        assertThat(loaded.getTopicIdByTitle("Anakin Skywalker")).isNotNull();

    }
}
