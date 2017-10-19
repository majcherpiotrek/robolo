package com.ksm.robolo.roboloapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ksm.robolo.roboloapp.domain.ProjectEntity;

public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {

    List<ProjectEntity> findAll();

    List<ProjectEntity> findAllByClientId(Long clientId);


}
