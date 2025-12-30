package com.devsxplore.thesis.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class Supervisor {
    Long id;
    String name;
    String kontakt;
    String fachgebiete;
    String informationsdatei;
    String links;
    List<String> themen;

    public Supervisor(String name, String kontakt, String fachgebiete, String informationsdatei, String links, List<String> themen) {
        this.name = name;
        this.kontakt = kontakt;
        this.fachgebiete = fachgebiete;
        this.informationsdatei = informationsdatei;
        this.links = links;
        this.themen = themen;
    }

    public static Supervisor createSupervisorWithoutId(String name, String kontakt, String fachgebiete, String informationsdatei, String links, List<String> themen) {
        return new Supervisor(name, kontakt, fachgebiete, informationsdatei, links, themen);
    }

    public static Supervisor createSupervisorWithId(Long id, String name, String kontakt, String fachgebiete, String informationsdatei, String links, List<String> themen) {
        return new Supervisor(id, name, kontakt, fachgebiete, informationsdatei, links, themen);
    }

    public void createsTopic(String topic){
        themen.add(topic);
    }
}
