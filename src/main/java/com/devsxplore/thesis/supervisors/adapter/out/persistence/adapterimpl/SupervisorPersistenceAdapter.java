package com.devsxplore.thesis.supervisors.adapter.out.persistence.adapterimpl;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import com.devsxplore.thesis.supervisors.adapter.out.persistence.jdbcentity.SupervisorJDBCEntity;
import com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper.SupervisorMapper;
import com.devsxplore.thesis.supervisors.adapter.out.persistence.repository.SupervisorRepository;
import com.devsxplore.thesis.supervisors.application.port.out.SupervisorRepositoryPort;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupervisorPersistenceAdapter implements SupervisorRepositoryPort {

	private final SupervisorRepository supervisorRepository;

	private final SupervisorMapper supervisorMapper;

	@Override
	public Supervisor save(Supervisor supervisor) {
		SupervisorJDBCEntity entity = this.supervisorMapper.mapSupervisorToJDBCEntity(supervisor);
		SupervisorJDBCEntity newSupervisor = this.supervisorRepository.save(entity);
		return this.supervisorMapper.mapSupervisorToDomainEntity(newSupervisor);
	}

	@Override
	public Optional<Supervisor> load(Long supervisorId) {
		return this.supervisorRepository.findByUserId(supervisorId)
				.map(this.supervisorMapper::mapSupervisorToDomainEntity);
	}

	@Override
	public Set<Supervisor> loadAll() {
		return this.supervisorRepository.findAll()
				.stream()
				.map(this.supervisorMapper::mapSupervisorToDomainEntity)
				.collect(Collectors.toSet());
	}

	@Override
	public boolean delete(Long supervisorId) {
		if (this.supervisorRepository.existsByUserId(supervisorId)) {
			this.supervisorRepository.deleteBySupervisorId(supervisorId);
			return true;
		}
		return false;
	}

	@Override
	public boolean existsBySupervisorUserId(Long supervisorUserId) {
		return this.supervisorRepository.existsByUserId(supervisorUserId);
	}

	@Override
	public Optional<Supervisor> loadByUserId(Long userId) {
		return this.supervisorRepository.findByUserId(userId).map(this.supervisorMapper::mapSupervisorToDomainEntity);
	}

	@Override
	public Optional<Supervisor> loadByPublicId(UUID publicId) {
		return this.supervisorRepository.findByPublicId(publicId)
				.map(this.supervisorMapper::mapSupervisorToDomainEntity);
	}

}
