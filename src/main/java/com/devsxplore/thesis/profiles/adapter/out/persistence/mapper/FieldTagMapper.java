package com.devsxplore.thesis.profiles.adapter.out.persistence.mapper;

import com.devsxplore.thesis.profiles.adapter.out.persistence.jdbcentity.FieldTagJDBCEntity;
import com.devsxplore.thesis.profiles.domain.model.FieldTag;

import java.util.Set;
import java.util.stream.Collectors;

public class FieldTagMapper {

    public static FieldTag mapFieldTagToDomainEntity(FieldTagJDBCEntity entity) {
        return new FieldTag(entity.fieldName());
    }

    public static FieldTagJDBCEntity mapFieldTagToJDBCEntity(FieldTag entity) {
        return new FieldTagJDBCEntity(entity.fieldName());
    }

    public static Set<FieldTag> mapFieldTagsToDomainEntities(Set<FieldTagJDBCEntity> entities) {
        return entities.stream()
                .map(FieldTagMapper::mapFieldTagToDomainEntity)
                .collect(Collectors.toSet());
    }

    public static Set<FieldTagJDBCEntity> mapFieldTagsToJDBCEntities(Set<FieldTag> entities) {
        return entities.stream()
                .map(FieldTagMapper::mapFieldTagToJDBCEntity)
                .collect(Collectors.toSet());
    }
}
