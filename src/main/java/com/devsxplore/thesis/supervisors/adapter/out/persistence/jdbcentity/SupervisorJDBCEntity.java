package com.devsxplore.thesis.supervisors.adapter.out.persistence.jdbcentity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
import java.util.UUID;

@Table("SUPERVISOR")
public record SupervisorJDBCEntity(@Id Long supervisorId, Long userId, UUID publicId, String title, String firstName,
		String lastName, String email, String office, String phone,
		@MappedCollection(idColumn = "SUPERVISOR_ID") Set<FieldTagJDBCEntity> fields,
		@MappedCollection(idColumn = "SUPERVISOR_ID") Set<TopicJDBCEntity> topics) {
}
