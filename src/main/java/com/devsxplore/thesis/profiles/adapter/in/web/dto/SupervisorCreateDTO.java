package com.devsxplore.thesis.profiles.adapter.in.web.dto;

public record SupervisorCreateDTO(
        String firstName,
        String lastName,
        String title,
        String email,
        String office,
        String phone
) {}
