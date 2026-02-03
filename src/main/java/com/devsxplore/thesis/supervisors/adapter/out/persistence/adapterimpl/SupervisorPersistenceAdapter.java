package com.devsxplore.thesis.supervisors.adapter.out.persistence.adapterimpl;

import com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper.SupervisorMapper;
import com.devsxplore.thesis.supervisors.adapter.out.persistence.jdbcentity.SupervisorJDBCEntity;
import com.devsxplore.thesis.supervisors.adapter.out.persistence.repository.SupervisorRepository;
import com.devsxplore.thesis.supervisors.application.port.out.SupervisorRepositoryPort;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper.SupervisorMapper.mapSupervisorToDomainEntity;
import static com.devsxplore.thesis.supervisors.adapter.out.persistence.mapper.SupervisorMapper.mapSupervisorToJDBCEntity;

@Component
@RequiredArgsConstructor
public class SupervisorPersistenceAdapter implements SupervisorRepositoryPort {

    private final SupervisorRepository supervisorRepository;

    @Override
    public Supervisor save(Supervisor supervisor) {
        var entity = mapSupervisorToJDBCEntity(supervisor);
        SupervisorJDBCEntity newSupervisor = supervisorRepository.save(entity);
        return mapSupervisorToDomainEntity(newSupervisor);
    }

    @Override
    public Optional<Supervisor> load(Long id) {
        return supervisorRepository.findById(id)
                .map(SupervisorMapper::mapSupervisorToDomainEntity);
    }

    @Override
    public List<Supervisor> loadAll() {
        return supervisorRepository.findAll()
                .stream()
                .map(SupervisorMapper::mapSupervisorToDomainEntity)
                .toList();
    }

    @Override
    public boolean delete(Long supervisorId) {
        if (supervisorRepository.existsById(supervisorId)) {
            supervisorRepository.deleteById(supervisorId);
            return true;
        }
        return false;
    }
}
