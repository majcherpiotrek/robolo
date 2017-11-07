package com.ksm.robolo.roboloapp.repository;

import com.ksm.robolo.roboloapp.domain.ProjectEntity;
import com.ksm.robolo.roboloapp.domain.UserEntity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;


public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {
    
    List<ProjectEntity> findAll();
}
