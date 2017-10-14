package com.ksm.robolo.roboloapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.ksm.robolo.roboloapp.domain.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

}
