package com.ksm.robolo.roboloapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.ksm.robolo.roboloapp.domain.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

}
