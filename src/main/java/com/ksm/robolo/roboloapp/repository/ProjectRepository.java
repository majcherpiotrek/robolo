package com.ksm.robolo.roboloapp.repository;

import com.ksm.robolo.roboloapp.domain.ProjectEntity;
import com.ksm.robolo.roboloapp.domain.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;


public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> findAllByUserEntityId(UUID userEntityId);

    List<ProjectEntity> findAllByUserEntityIdAndClientId(UUID userEntityId, Long clientId);
    
    ProjectEntity findById(Long id);
}
