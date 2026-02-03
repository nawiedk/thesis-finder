package com.devsxplore.thesis.supervisors.adapter.out.persistence.jdbcentity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("TOPIC")
public record TopicJDBCEntity(@Id Long id,
                              String title,
                              String description) {
}
