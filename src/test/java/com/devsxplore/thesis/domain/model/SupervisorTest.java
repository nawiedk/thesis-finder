package com.devsxplore.thesis.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.devsxplore.thesis.domain.model.Supervisor.createSupervisorWithId;
import static com.devsxplore.thesis.domain.model.Supervisor.createSupervisorWithoutId;
import static org.assertj.core.api.Assertions.*;

public class SupervisorTest {

    private long id;
    private String name;
    private String kontakt;
    private String fachgebiete;
    private String informationsdatei;
    private String links;
    private String thema;
    private List<String> themen;

    @BeforeEach
    void prepareSupervisorData(){
        id = 1L;
        name = "Max Mustermann";
        kontakt = "Kontakt";
        fachgebiete = "Fachgebiete";
        informationsdatei = "Informationsdatei";
        links = "Links";
        thema = "Thema";
        themen = new ArrayList<String>();
    }


    @Test
    @DisplayName("Create Supervisor Without Id")
    void createSupervisorWithoutIdTest() {
        Supervisor supervisor = createSupervisorWithoutId(name, kontakt, fachgebiete, informationsdatei, links, themen);
        assertThat(supervisor).isNotNull();
        assertThat(supervisor.getName()).isEqualTo("Max Mustermann");
    }

    @Test
    @DisplayName("Create Supervisor With Id")
    void createSupervisorWithIdTest() {
        Supervisor supervisor = createSupervisorWithId(id, name, kontakt, fachgebiete, informationsdatei, links, themen);
        assertThat(supervisor).isNotNull();
        assertThat(supervisor.getId()).isEqualTo(1L);
    }


    @Test
    @DisplayName("Supervisor creates Topic")
    void supervisorCreatesTopicTest() {
        Supervisor supervisor = createSupervisorWithId(id,name,kontakt,fachgebiete,informationsdatei,links, themen);
        supervisor.createsTopic(thema);
        assertThat(supervisor.getThemen()).contains("Thema");
    }

    @Test
    @DisplayName("If supervisor creates 2 topics then the topic list is size of 2")
    void supervisorCreatesTwoTopics(){
        Supervisor supervisor = createSupervisorWithId(id, name, kontakt, fachgebiete, informationsdatei, links, themen);
        supervisor.createsTopic("Thema1");
        supervisor.createsTopic("Thema2");
        assertThat(supervisor.getThemen().size()).isEqualTo(2);
    }
}
