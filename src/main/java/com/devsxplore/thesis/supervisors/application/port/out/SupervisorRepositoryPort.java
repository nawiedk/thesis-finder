package com.devsxplore.thesis.supervisors.application.port.out;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;

public interface SupervisorRepositoryPort {

	Supervisor save(Supervisor supervisor);

	Optional<Supervisor> load(Long supervisorId);

	Set<Supervisor> loadAll();

	boolean delete(Long supervisorId);

	boolean existsBySupervisorUserId(Long SupervisorUserId);

	Optional<Supervisor> loadByUserId(Long userId);

	Optional<Supervisor> loadByPublicId(UUID publicId);

}
