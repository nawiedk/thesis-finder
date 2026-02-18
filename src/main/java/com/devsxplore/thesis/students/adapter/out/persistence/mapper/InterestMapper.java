package com.devsxplore.thesis.students.adapter.out.persistence.mapper;

import com.devsxplore.thesis.students.adapter.out.persistence.jdbcentity.InterestJDBCEntity;
import com.devsxplore.thesis.students.domain.model.Interest;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InterestMapper {

	public Interest mapInterestToDomainEntity(InterestJDBCEntity entity) {
		return new Interest(entity.interest());
	}

	public InterestJDBCEntity mapInterestToJDBCEntity(Interest interest) {
		return new InterestJDBCEntity(interest.interest());
	}

	public Set<Interest> mapInterestsToDomainEntities(Set<InterestJDBCEntity> entities) {
		return entities.stream().map(this::mapInterestToDomainEntity).collect(Collectors.toSet());
	}

	public Set<InterestJDBCEntity> mapInterestsToJDBCEntities(Set<Interest> interests) {
		return interests.stream().map(this::mapInterestToJDBCEntity).collect(Collectors.toSet());
	}

}
