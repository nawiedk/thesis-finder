package com.devsxplore.thesis.supervisors.application.port.in.command.supervisor;

import java.util.UUID;

public record LoadByPublicIdCommand(UUID publicId) {
}
