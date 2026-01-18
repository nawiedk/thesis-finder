package com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("SUPERVISOR")
public record SupervisorJDBCEntity(@Id Long id,
                                   String title,
                                   String firstName,
                                   String lastName,
                                   String email,
                                   String office,
                                   String phone,
                                   @MappedCollection(idColumn = "SUPERVISOR_ID", keyColumn = "SUPERVISOR_KEY")
                                   List<TopicJDBCEntity> topics
) {}
