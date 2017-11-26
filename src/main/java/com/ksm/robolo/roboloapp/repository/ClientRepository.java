package com.ksm.robolo.roboloapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.ksm.robolo.roboloapp.domain.ClientEntity;

public interface ClientRepository extends CrudRepository<ClientEntity, Long> {
	
	ClientEntity findById(Long id);

}
