package com.devsxplore.thesis.students.adapter.out.persistence;

import com.devsxplore.thesis.students.adapter.out.persistence.jdbcentity.StudentJDBCEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<StudentJDBCEntity, Long> {
    Optional<StudentJDBCEntity> findByStudentUserId(Long studentUserId);

    boolean existsByStudentUserId(Long studentUserId);
}
