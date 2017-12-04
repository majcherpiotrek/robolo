package com.ksm.robolo.roboloapp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ksm.robolo.roboloapp.domain.WorkerEntity;

public interface WorkerRepository extends JpaRepository<WorkerEntity, Long> {

	List<WorkerEntity> findAllByUserEntityId(UUID userEntityId);

	WorkerEntity findById(Long workerId);

}
