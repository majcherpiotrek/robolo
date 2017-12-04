package com.ksm.robolo.roboloapp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ksm.robolo.roboloapp.domain.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
	
	ClientEntity findById(Long id);
	
	List<ClientEntity> findAllByUserEntityId(UUID userEntityId);
}
