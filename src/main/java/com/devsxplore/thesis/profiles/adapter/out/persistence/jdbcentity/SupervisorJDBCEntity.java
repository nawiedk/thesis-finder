package com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("SUPERVISOR")
public record SupervisorJDBCEntity(@Id Long id,
                                   String title,
                                   String firstName,
                                   String lastName,
                                   String email,
                                   String office,
                                   String phone,
                                   @MappedCollection(idColumn = "SUPERVISOR_ID")
                                   Set<FieldTagJDBCEntity> fields,
                                   @MappedCollection(idColumn = "SUPERVISOR_ID")
                                   Set<TopicJDBCEntity> topics
) {}
