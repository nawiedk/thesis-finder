package com.devsxplore.thesis.profiles.adapter.in.web.dto.supervisor;

public record SupervisorUpdateDTO(
        String firstName,
        String lastName,
        String title,
        String email,
        String office,
        String phone
) {}
