package com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity;

import org.springframework.data.relational.core.mapping.Table;

@Table("FIELD_TAG")
public record FieldTagJDBCEntity(
        String fieldName
) {}