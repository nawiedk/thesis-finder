package com.devsxplore.thesis.students.adapter.out.persistence.jdbcentity;

import org.springframework.data.relational.core.mapping.Table;

@Table("INTEREST")
public record InterestJDBCEntity(String interest) {
}
