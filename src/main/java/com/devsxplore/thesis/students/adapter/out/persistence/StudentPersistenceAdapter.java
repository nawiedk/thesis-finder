package com.devsxplore.thesis.students.adapter.out.persistence;

import com.devsxplore.thesis.students.adapter.out.persistence.jdbcentity.StudentJDBCEntity;
import com.devsxplore.thesis.students.adapter.out.persistence.mapper.StudentMapper;
import com.devsxplore.thesis.students.application.port.out.StudentRepositoryPort;
import com.devsxplore.thesis.students.domain.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StudentPersistenceAdapter implements StudentRepositoryPort {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public Student save(Student student) {
        StudentJDBCEntity entity = studentRepository.save(studentMapper.mapStudentToJDBCEntity(student));
        return studentMapper.mapStudentToDomainEntity(entity);
    }

    @Override
    public Optional<Student> load(Long studentId) {
        return studentRepository.findById(studentId)
                .map(studentMapper::mapStudentToDomainEntity);
    }

    @Override
    public Optional<Student> loadByStudentUserId(Long studentUserId) {
        return studentRepository.findByStudentUserId(studentUserId)
                .map(studentMapper::mapStudentToDomainEntity);
    }

    @Override
    public boolean existsByStudentUserId(Long studentUserId) {
        return studentRepository.existsByStudentUserId(studentUserId);
    }
}
