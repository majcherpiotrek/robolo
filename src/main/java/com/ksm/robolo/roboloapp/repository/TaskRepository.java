package com.ksm.robolo.roboloapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.ksm.robolo.roboloapp.domain.TaskEntity;

public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

}
