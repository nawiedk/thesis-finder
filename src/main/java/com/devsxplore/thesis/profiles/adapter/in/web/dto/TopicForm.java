package com.devsxplore.thesis.profiles.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
//TODO:Zu TopicCreateForm umändern
public record TopicForm(Long id, String topic, String description) {

}
