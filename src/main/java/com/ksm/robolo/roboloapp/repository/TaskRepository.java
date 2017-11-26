package com.ksm.robolo.roboloapp.repository;

import com.ksm.robolo.roboloapp.domain.ProjectEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.ksm.robolo.roboloapp.domain.TaskEntity;

import java.util.List;

import javax.transaction.Transactional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByProject_Id(Long id);
    
    TaskEntity findById(Long id);
    
    @Transactional
    void deleteById(Long id);
    
    @Transactional
    void deleteByProject(ProjectEntity project);
}
