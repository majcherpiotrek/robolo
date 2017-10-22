package com.ksm.robolo.roboloapp.repository;

import com.ksm.robolo.roboloapp.domain.ProjectEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {

    List<ProjectEntity> findAll();

    List<ProjectEntity> findAllByClientId(Long clientId);


}
