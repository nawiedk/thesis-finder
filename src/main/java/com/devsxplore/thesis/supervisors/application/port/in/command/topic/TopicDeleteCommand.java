package com.devsxplore.thesis.supervisors.application.port.in.command.topic;

import jakarta.validation.constraints.NotNull;

public record TopicDeleteCommand(@NotNull Long topicId) {
}
