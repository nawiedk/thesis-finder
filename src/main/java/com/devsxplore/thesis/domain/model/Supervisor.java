package com.devsxplore.thesis.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Supervisor {
    Long id;
    String name;
    String kontakt;
    String fachgebiete;
    String informationsdatei;
    String links;

    public Supervisor(String name, String kontakt, String fachgebiete, String informationsdatei, String links) {
        this.name = name;
        this.kontakt = kontakt;
        this.fachgebiete = fachgebiete;
        this.informationsdatei = informationsdatei;
        this.links = links;
    }

    public static Supervisor createSupervisorWithoutId(String name, String kontakt, String fachgebiete, String informationsdatei, String links) {
        return new Supervisor(name, kontakt, fachgebiete, informationsdatei, links);
    }

    public static Supervisor createSupervisorWithId(Long id, String name, String kontakt, String fachgebiete, String informationsdatei, String links) {
        return new Supervisor(id, name, kontakt, fachgebiete, informationsdatei, links);
    }
}
