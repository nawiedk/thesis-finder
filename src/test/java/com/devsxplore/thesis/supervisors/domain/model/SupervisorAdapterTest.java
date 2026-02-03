package com.devsxplore.thesis.supervisors.domain.model;

import com.devsxplore.thesis.supervisors.adapter.out.persistence.adapterimpl.SupervisorPersistenceAdapter;
import com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper.SupervisorMapper;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.CreateTopicCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.SaveChangesTopicCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.ShowTopicListCommand;
import com.devsxplore.thesis.supervisors.application.service.SupervisorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static com.devsxplore.thesis.supervisors.domain.model.SupervisorFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import({SupervisorPersistenceAdapter.class, SupervisorMapper.class, SupervisorService.class})
@DataJdbcTest
public class SupervisorAdapterTest {

    @Autowired
    SupervisorPersistenceAdapter adapter;


    @Test
    @Sql("/com/devsxplore/thesis/supervisors/domain/model/clear_data.sql")
    @DisplayName("When a supervisor is saved with supervisorId the supevisor you load with same supervisorId has the same attributes")
    void test_1() {
        Supervisor supervisor = createSupervisorWithNoTopicsAndNoId();
        Supervisor supervisorSaved = adapter.save(supervisor);

        Supervisor supervisorLoaded = adapter.load(supervisorSaved.getSupervisorId());

        assertThat(supervisorLoaded.getFullName()).isEqualTo(supervisor.getFullName());
    }

    @Test
    @Sql("/com/devsxplore/thesis/supervisors/domain/model/clear_data.sql")
    @DisplayName("When supervisor is saved with topics when loaded it has the same topics")
    void test_2() {
        Supervisor saved = adapter.save( createSupervisorWithTopicsAndNoId());
        Supervisor loaded = adapter.load(saved.getSupervisorId());

        assertThat(loaded.getTopics().size()).isEqualTo(2);
    }

    @Test
    @Sql("/com/devsxplore/thesis/supervisors/domain/model/clear_data.sql")
    @DisplayName("When supervisor title is updated saved and loaded title attributes are changed ")
    void test_3() {
        Supervisor saved = adapter.save( createSupervisorWithTopicsAndNoId());
        Supervisor loaded = adapter.load(saved.getSupervisorId()).orElseThrow();
        loaded.updateTopic(1L,"Anakin Skywalker", "Jedi");
        Supervisor savedAgain = adapter.save(loaded);
        Supervisor loadedAgain = adapter.load(saved.getSupervisorId()).orElseThrow();

        assertThat(loadedAgain.getTopics().getFirst().getTitle()).isEqualTo("Anakin Skywalker");
        assertThat(saved.getTopics().getFirst().getTitle()).isEqualTo("Thema1");
    }

    //TODO Integrationstests zwischen service und adapter
    //TODO: ich hab verkackt da wenn ich Topic ändere auf weitere Attribute muss ich alle benutzten commands in den tests abändern </3
    @Test
    @Sql("/com/devsxplore/thesis/supervisors/domain/model/clear_data.sql")
    @DisplayName("createTopic correctly saves a title in database")
    void test_4() {
        SupervisorService service = new SupervisorService(adapter);
        Supervisor supervisor = adapter.save(createSupervisorWithNoTopicsAndNoId());
        CreateTopicCommand command = new CreateTopicCommand(supervisor.getSupervisorId(),"Bruce Wayne", "Batman");
        Topic topic = service.createTopic(command);

        assertThat(topic.getTitle()).isEqualTo("Bruce Wayne");
    }

    @Test
    @Sql("/com/devsxplore/thesis/supervisors/domain/model/clear_data.sql")
    @DisplayName("showTopicList correctly Loads topics from database")
    void test_5() {
        Supervisor supervisor = adapter.save(createSupervisorWithTopicsAndNoId());

        SupervisorService service = new SupervisorService(adapter);
        ShowTopicListCommand command = new ShowTopicListCommand(supervisor.getSupervisorId());

        assertThat(service.loadTopicsBySupervisor(command)).hasSize(2);
    }

    @Test
    @Sql("/com/devsxplore/thesis/supervisors/domain/model/clear_data.sql")
    @DisplayName("showTopicList correctly Loads topics from database")
    void test_6() {
        Supervisor supervisor = adapter.save(createSupervisorWithTopicsAndNoId());

        SupervisorService service = new SupervisorService(adapter);
        SaveChangesTopicCommand command = new SaveChangesTopicCommand(supervisor.getSupervisorId(), 1L, "Anakin Skywalker", "Jedi");

        Topic topic = service.updateTopic(command);

        assertThat(topic.getTitle()).isEqualTo("Anakin Skywalker");

        Supervisor loaded = adapter.load(supervisor.getSupervisorId());
        assertThat(loaded.getTopicIdByTitle("Anakin Skywalker")).isNotNull();

    }
}
