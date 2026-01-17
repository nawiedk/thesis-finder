package com.devsxplore.thesis.profiles.adapter.out.persistence.adapterimpl;

import com.devsxplore.thesis.profiles.adapter.out.persistence.mapper.SupervisorMapper;
import com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity.SupervisorJDBCEntity;
import com.devsxplore.thesis.profiles.adapter.out.persistence.repository.SupervisorRepository;
import com.devsxplore.thesis.profiles.application.port.out.SupervisorRepositoryPort;
import com.devsxplore.thesis.profiles.domain.model.Supervisor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupervisorPersistenceAdapter implements SupervisorRepositoryPort {

    private final SupervisorRepository supervisorRepository;
    private final SupervisorMapper supervisorMapper;

    @Override
    public Supervisor save(Supervisor supervisor) {
        SupervisorJDBCEntity entity = supervisorMapper.toJDBCEntity(supervisor);
        var newSupervisor = supervisorRepository.save(entity);
        return supervisorMapper.mapToDomainEntity(newSupervisor);
    }
}
