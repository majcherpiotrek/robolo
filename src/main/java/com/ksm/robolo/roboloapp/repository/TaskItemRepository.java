package com.ksm.robolo.roboloapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.ksm.robolo.roboloapp.domain.TaskItemEntity;

public interface TaskItemRepository extends CrudRepository<TaskItemEntity, Long> {

}
