package com.ksm.robolo.roboloapp.repository;

import com.ksm.robolo.roboloapp.domain.ProjectEntity;
import org.springframework.data.repository.CrudRepository;

import com.ksm.robolo.roboloapp.domain.TaskEntity;

import java.util.List;

public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

    List<TaskEntity> findByProject_Id(Long id);
}
