package com.devsxplore.thesis.supervisors.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.devsxplore.thesis.supervisors.domain.model.SupervisorFactory.createSupervisorWithNoTopics;
import static org.assertj.core.api.Assertions.assertThat;

class SupervisorTest {

    @Test
    @DisplayName("Ein Supervisor kann ein Thema hinzufügen")
    void should_add_topic_to_supervisor() {
        Supervisor supervisor = createSupervisorWithNoTopics();

        supervisor.addTopic("Neues Thema", "Beschreibung");

        assertThat(supervisor.getTopics()).hasSize(1);
        assertThat(supervisor.getTopics().iterator().next().getTitle()).isEqualTo("Neues Thema");
    }

    @Test
    @DisplayName("Ein Supervisor kann mehrere Themen hinzufügen")
    void should_add_multiple_topics() {
        Supervisor supervisor = createSupervisorWithNoTopics();

        supervisor.addTopic("Thema 1", "Desc 1");
        supervisor.addTopic("Thema 2", "Desc 2");

        assertThat(supervisor.getTopics()).hasSize(2);
    }
}