package com.devsxplore.thesis.profiles.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.devsxplore.thesis.profiles.domain.model.Supervisor.createSupervisorWithId;
import static com.devsxplore.thesis.profiles.domain.model.Supervisor.createSupervisorWithoutId;
import static org.assertj.core.api.Assertions.assertThat;

public class SupervisorTest {

    private SupervisorId id;
    private Name name;
    private Email email;
    private Contact contact;
    private List<FieldTag> fields;

    @BeforeEach
    void prepareSupervisorData() {
        id = new SupervisorId(1L);
        name = new Name("Nawied", "Khaleqi", AcademicTitle.NONE);
        email = new Email("nawied@hhu.de");
        contact = new Contact(email, "25.irgendwo", null);
        fields = List.of(new FieldTag("ProPra 1"),
                new FieldTag("ProPra 2"));
    }


    @Test
    @DisplayName("Create Supervisor Without Id")
    void createSupervisorWithoutIdTest() {
        Supervisor supervisor = createSupervisorWithoutId(name, contact);
        assertThat(supervisor).isNotNull();
        assertThat(supervisor.getFullName()).isEqualTo("Nawied Khaleqi");
    }

    @Test
    @DisplayName("Create Supervisor With Id")
    void createSupervisorWithIdTest() {
        Supervisor supervisor = createSupervisorWithId(id, name, contact);
        assertThat(supervisor).isNotNull();
        assertThat(supervisor.getId().id()).isEqualTo(1L);
        assertThat(supervisor.getFullName()).isEqualTo("Nawied Khaleqi");
    }
}
