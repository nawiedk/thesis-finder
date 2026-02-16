package com.devsxplore.thesis.students.adapter.in.web.dto.response;

import java.util.Set;

public record UserProfileDTO(
        String firstName,
        String lastName,
        String fullName,
        Set<String> courses,
        Set<String> interests
) {
}
