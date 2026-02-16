package com.devsxplore.thesis.supervisors.adapter.out.persistence.adapterimpl;

import com.devsxplore.thesis.supervisors.adapter.out.persistence.jdbcentity.SupervisorJDBCEntity;
import com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper.SupervisorMapper;
import com.devsxplore.thesis.supervisors.adapter.out.persistence.repository.SupervisorRepository;
import com.devsxplore.thesis.supervisors.application.port.out.SupervisorRepositoryPort;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SupervisorPersistenceAdapter implements SupervisorRepositoryPort {

    private final SupervisorRepository supervisorRepository;
    private final SupervisorMapper supervisorMapper;

    @Override
    public Supervisor save(Supervisor supervisor) {
        SupervisorJDBCEntity entity = supervisorMapper.mapSupervisorToJDBCEntity(supervisor);
        SupervisorJDBCEntity newSupervisor = supervisorRepository.save(entity);
        return supervisorMapper.mapSupervisorToDomainEntity(newSupervisor);
    }

    @Override
    public Optional<Supervisor> load(Long supervisorId) {
        return supervisorRepository.findByUserId(supervisorId)
                .map(supervisorMapper::mapSupervisorToDomainEntity);
    }

    @Override
    public List<Supervisor> loadAll() {
        return supervisorRepository.findAll()
                .stream()
                .map(supervisorMapper::mapSupervisorToDomainEntity)
                .toList();
    }

    @Override
    public boolean delete(Long supervisorId) {
        if (supervisorRepository.existsByUserId(supervisorId)) {
            supervisorRepository.deleteBySupervisorId(supervisorId);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsBySupervisorUserId(Long supervisorUserId) {
        return supervisorRepository.existsByUserId(supervisorUserId);
    }

    @Override
    public Optional<Supervisor> loadByUserId(Long userId) {
        return supervisorRepository.findByUserId(userId)
                .map(supervisorMapper::mapSupervisorToDomainEntity);
    }
}
