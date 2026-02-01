package com.devsxplore.thesis.profiles.adapter.out.persistence.repository;

import com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity.SupervisorJDBCEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupervisorRepository extends CrudRepository<SupervisorJDBCEntity, Long> {
    @Override
    List<SupervisorJDBCEntity> findAll();
}
