package com.devsxplore.thesis.students.adapter.out.persistence.jdbcentity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("STUDENT")
public record StudentJDBCEntity(
        @Id Long studentId,
        Long studentUserId,
        String firstName,
        String lastName,
        @MappedCollection(idColumn = "STUDENT_ID")
        Set<CourseJDBCEntity> courses,
        @MappedCollection(idColumn = "STUDENT_ID")
        Set<InterestJDBCEntity> interests
) {
}
