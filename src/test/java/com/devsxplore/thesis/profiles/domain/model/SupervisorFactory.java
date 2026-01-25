package com.devsxplore.thesis.profiles.domain.model;

public class SupervisorFactory {

    public static Supervisor createSupervisorWithNoTopics(){
        return Supervisor.createSupervisorWithId(new SupervisorId(1L),
                new Name("Bruce", "Wayne", AcademicTitle.PROF),
                new Contact(new Email("jawsorca@hhu.de"), "office", "+491234567"));
    }

    public static Supervisor createSupervisorWithTopics(){
        Supervisor supervisor = Supervisor.createSupervisorWithId(new SupervisorId(1L),
                new Name("Bruce", "Wayne", AcademicTitle.PROF),
                new Contact(new Email("jawsorca@hhu.de"), "office", "+491234567"));

        supervisor.addTopicWithId(1L,"Thema1", "Beschreibung1");
        supervisor.addTopicWithId(2L, "Thema2", "Beschreibung2");

        return supervisor;
    }

    public static Supervisor createSupervisorWithNoTopicsAndNoId(){
        return Supervisor.createSupervisorWithoutId(
                new Name("Bruce", "Wayne", AcademicTitle.PROF),
                new Contact(new Email("jawsorca@hhu.de"), "office", "+491234567"));
    }

    public static Supervisor createSupervisorWithTopicsAndNoId(){
        Supervisor supervisor = Supervisor.createSupervisorWithoutId(
                new Name("Bruce", "Wayne", AcademicTitle.PROF),
                new Contact(new Email("jawsorca@hhu.de"), "office", "+491234567"));

        supervisor.addTopicWithId(1L,"Thema1", "Beschreibung1");
        supervisor.addTopicWithId( 2L,"Thema2", "Beschreibung2");

        return supervisor;
    }
}
