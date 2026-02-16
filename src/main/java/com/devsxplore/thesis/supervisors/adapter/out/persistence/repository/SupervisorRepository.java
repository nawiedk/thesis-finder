package com.devsxplore.thesis.supervisors.adapter.out.persistence.repository;

import com.devsxplore.thesis.supervisors.adapter.out.persistence.jdbcentity.SupervisorJDBCEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupervisorRepository extends CrudRepository<SupervisorJDBCEntity, Long> {
    @Override
    List<SupervisorJDBCEntity> findAll();

    Optional<SupervisorJDBCEntity> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    void deleteBySupervisorId(Long supervisorId);
}
