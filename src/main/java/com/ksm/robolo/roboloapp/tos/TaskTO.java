package com.ksm.robolo.roboloapp.tos;

import com.ksm.robolo.roboloapp.domain.TaskItemEntity;
import com.ksm.robolo.roboloapp.enums.TaskStatus;

import java.util.Date;
import java.util.List;

public class TaskTO {

    private Long id;

    private String description;

    private Integer estimatedTaskDuration;

    private List<WorkerTO> workers;

    private Date creationDate;

    private Date startDate;

    private TaskStatus status;

    private List<TaskItemEntity> taskItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEstimatedTaskDuration() {
        return estimatedTaskDuration;
    }

    public void setEstimatedTaskDuration(Integer estimatedTaskDuration) {
        this.estimatedTaskDuration = estimatedTaskDuration;
    }

    public List<WorkerTO> getWorkers() {
        return workers;
    }

    public void setWorkers(List<WorkerTO> workers) {
        this.workers = workers;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public List<TaskItemEntity> getTaskItems() {
        return taskItems;
    }

    public void setTaskItems(List<TaskItemEntity> taskItems) {
        this.taskItems = taskItems;
    }
}
