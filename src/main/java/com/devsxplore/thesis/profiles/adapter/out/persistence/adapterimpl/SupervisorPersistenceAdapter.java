package com.devsxplore.thesis.profiles.adapter.out.persistence.adapterimpl;

import com.devsxplore.thesis.profiles.adapter.out.persistence.mapper.SupervisorMapper;
import com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity.SupervisorJDBCEntity;
import com.devsxplore.thesis.profiles.adapter.out.persistence.repository.SupervisorRepository;
import com.devsxplore.thesis.profiles.application.port.out.SupervisorRepositoryPort;
import com.devsxplore.thesis.profiles.domain.model.Supervisor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SupervisorPersistenceAdapter implements SupervisorRepositoryPort {

    private final SupervisorRepository supervisorRepository;
    private final SupervisorMapper supervisorMapper;

    @Override
    public Supervisor save(Supervisor supervisor) {
        var entity = supervisorMapper.toJDBCEntity(supervisor);
        var newSupervisor = supervisorRepository.save(entity);
        return supervisorMapper.mapToDomainEntity(newSupervisor);
    }

    @Override
    public Supervisor load(Long id) {
        Optional<SupervisorJDBCEntity> entity = supervisorRepository.findById(id);
        return supervisorMapper.mapToDomainEntity(entity.orElseThrow());
    }
}
