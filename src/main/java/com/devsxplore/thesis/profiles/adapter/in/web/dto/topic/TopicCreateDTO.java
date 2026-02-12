package com.devsxplore.thesis.profiles.adapter.in.web.dto.topic;

import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

public record TopicCreateDTO (
        Long supervisorId,
        @NotBlank(message = "Title darf nicht leer sein")
        String title,
        String description,
        Set<String> links,
        //fields soll fields benutzen.
        Set<String> fields

){
        public TopicCreateDTO {
                if(links == null){
                        links = new HashSet<>();
                }

                if(fields == null){
                        fields = new HashSet<>();
                }
        }
}
