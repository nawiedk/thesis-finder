package com.devsxplore.thesis.accounts.adapter.out.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccountJDBCEntity, Long> {

	@Override
	List<UserAccountJDBCEntity> findAll();

}
