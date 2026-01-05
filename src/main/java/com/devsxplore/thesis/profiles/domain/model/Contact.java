package com.devsxplore.thesis.profiles.domain.model;

import java.util.regex.Pattern;

public record Contact(Email email, String office, String phone) {

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(?:\\+49|0)[1-9][\\d\\s/-]{5,20}$"
    );

    public Contact {
        if (email == null)
            throw new IllegalArgumentException("Email cannot be null");
        if (office == null || office.isBlank())
            office = "";
        else
            office = office.trim();
        if (phone == null || phone.isBlank())
            phone = "";
        else {
            phone = phone.trim();
            if (!PHONE_PATTERN.matcher(phone).matches())
                throw new IllegalArgumentException("Invalid phone number");
        }
    }
}