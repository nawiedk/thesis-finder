package com.devsxplore.thesis.supervisors.adapter.out.persistence;

import com.devsxplore.thesis.supervisors.adapter.out.persistence.adapterimpl.SupervisorPersistenceAdapter;
import com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper.FieldTagMapper;
import com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper.SupervisorMapper;
import com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper.TopicMapper;
import com.devsxplore.thesis.supervisors.adapter.out.persistence.repository.SupervisorRepository;
import com.devsxplore.thesis.supervisors.domain.model.AcademicTitle;
import com.devsxplore.thesis.supervisors.domain.model.Name;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static com.devsxplore.thesis.supervisors.domain.model.SupervisorFactory.createSupervisorWithNoTopics;
import static com.devsxplore.thesis.supervisors.domain.model.SupervisorFactory.createSupervisorWithTopics;
import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import({SupervisorPersistenceAdapter.class, SupervisorMapper.class, TopicMapper.class, FieldTagMapper.class})
class SupervisorAdapterTest {

    @Autowired
    SupervisorPersistenceAdapter adapter;

    @Autowired
    SupervisorRepository repository;

    @Test
    @DisplayName("Supervisor kann gespeichert und geladen werden")
    void should_save_and_load_supervisor() {
        Supervisor supervisor = createSupervisorWithTopics();

        Supervisor saved = adapter.save(supervisor);
        Optional<Supervisor> loaded = adapter.load(saved.getSupervisorId());

        assertThat(loaded).isPresent();
        assertThat(loaded.get().getFirstName()).isEqualTo(supervisor.getFirstName());
        assertThat(loaded.get().getTopics()).isNotEmpty();
    }

    @Test
    void updateProfile_ShouldKeepOldValues_WhenNewValuesAreBlank() {
        Supervisor supervisor = createSupervisorWithNoTopics();
        String oldFirstName = supervisor.getFirstName();

        supervisor.updateProfile("", "  ", null, "valid@hhu.de", "", null);

        assertThat(supervisor.getFirstName()).isEqualTo(oldFirstName);
    }

    @Test
    void getFullName_ShouldIncludeTitle_OnlyIfPresent() {
        Name nameWithTitle = new Name("Max", "Mustermann", AcademicTitle.DR);
        Name nameWithoutTitle = new Name("Max", "Mustermann", AcademicTitle.NONE);

        assertThat(nameWithTitle.getFullName()).isEqualTo("Dr. Max Mustermann");
        assertThat(nameWithoutTitle.getFullName()).isEqualTo("Max Mustermann");
    }

    @Test
    void addField_ShouldNotAddDuplicateFields() {
        Supervisor supervisor = createSupervisorWithNoTopics();

        supervisor.addField("Java");
        supervisor.addField("java");
        supervisor.addField(" Java ");

        assertThat(supervisor.getFields()).hasSize(1);
    }
}