package com.devsxplore.thesis.supervisors.domain.model;

public class SupervisorFactory {

    public static Supervisor createSupervisorWithNoTopics() {
        return Supervisor.createSupervisorWithoutId(
                new UserId(10L),
                new Name("Bruce", "Wayne", AcademicTitle.NONE),
                Contact.contactFromPrimitive("bruce.wayne@hhu.de", "Bat Cave", "111")
        );
    }

    public static Supervisor createSupervisorWithTopics() {
        Supervisor supervisor = createSupervisorWithNoTopics();
        supervisor.addTopic("Gotham retten", "Den Joker besiegen");
        supervisor.addTopic("Alfred helfen", "Geld zu machen");
        return supervisor;
    }
}