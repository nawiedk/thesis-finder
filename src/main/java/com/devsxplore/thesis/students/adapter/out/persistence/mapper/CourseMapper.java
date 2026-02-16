package com.devsxplore.thesis.students.adapter.out.persistence.mapper;

import com.devsxplore.thesis.students.adapter.out.persistence.jdbcentity.CourseJDBCEntity;
import com.devsxplore.thesis.students.domain.model.Course;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public Course mapCourseToDomainEntity(CourseJDBCEntity entity) {
        return new Course(entity.course());
    }

    public CourseJDBCEntity mapCourseToJDBCEntity(Course course) {
        return new CourseJDBCEntity(course.course());
    }

    public Set<Course> mapCoursesToDomainEntities(Set<CourseJDBCEntity> entities) {
        return entities.stream()
                .map(this::mapCourseToDomainEntity)
                .collect(Collectors.toSet());
    }

    public Set<CourseJDBCEntity> mapCoursesToJDBCEntities(Set<Course> courses) {
        return courses.stream()
                .map(this::mapCourseToJDBCEntity)
                .collect(Collectors.toSet());
    }
}
