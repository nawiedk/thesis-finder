package com.devsxplore.thesis.students.application.port.out;

import com.devsxplore.thesis.students.domain.model.Student;

import java.util.Optional;

public interface StudentRepositoryPort {

    Student save(Student student);

    Optional<Student> load(Long studentId);

    Optional<Student> loadByStudentUserId(Long studentUserId);

    boolean existsByStudentUserId(Long studentUserId);


}
