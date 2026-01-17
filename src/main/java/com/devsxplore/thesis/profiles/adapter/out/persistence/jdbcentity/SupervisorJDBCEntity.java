package com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("supervisor")
public record SupervisorJDBCEntity(@Id Long id,
                                   String title,
                                   String firstName,
                                   String lastName,
                                   String email,
                                   String office,
                                   String phone
) {}
