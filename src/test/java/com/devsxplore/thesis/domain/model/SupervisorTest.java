package com.devsxplore.thesis.domain.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.devsxplore.thesis.domain.model.Supervisor.createSupervisorWithId;
import static com.devsxplore.thesis.domain.model.Supervisor.createSupervisorWithoutId;
import static org.assertj.core.api.Assertions.*;

public class SupervisorTest {


    @Test
    @DisplayName("Create Supervisor Without Id")
    void createSupervisorWithoutIdTest() {
        String name = "Max Mustermann";
        String kontakt = "Kontakt";
        String fachgebiete = "Fachgebiete";
        String informationsdatei = "Informationsdatei";
        String links = "Links";

        Supervisor supervisor = createSupervisorWithoutId(name, kontakt, fachgebiete, informationsdatei, links);

        assertThat(supervisor).isNotNull();
        assertThat(supervisor.getName()).isEqualTo("Max Mustermann");
    }

    @Test
    @DisplayName("Create Supervisor With Id")
    void createSupervisorWithIdTest() {
        Long id = 1L;
        String name = "Max Mustermann";
        String kontakt = "Kontakt";
        String fachgebiete = "Fachgebiete";
        String informationsdatei = "Informationsdatei";
        String links = "Links";

        Supervisor supervisor = createSupervisorWithId(id, name, kontakt, fachgebiete, informationsdatei, links);

        assertThat(supervisor).isNotNull();
        assertThat(supervisor.getId()).isEqualTo(1L);
    }


    @Test
    @Disabled
    @DisplayName("Betreuer erstellt Thema")
    void supervisorCreatesTopicTest() {
//      TODO: implement test

    }
}
