package com.ksm.robolo.roboloapp.domain;

import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ksm.robolo.roboloapp.enums.TaskStatus;

@Entity
@Table(name = "Tasks")
public class TaskEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String description;
	
	private Period estimatedTaskDuration;
	
	@ManyToMany(targetEntity = WorkerEntity.class)
	private Set<WorkerEntity> workersSet;
	
	@ManyToOne
	private ProjectEntity project;
	
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	private TaskStatus status;
	
	@OneToMany(targetEntity = TaskItemEntity.class)
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

	public Period getEstimatedTaskDuration() {
		return estimatedTaskDuration;
	}

	public void setEstimatedTaskDuration(Period estimatedTaskDuration) {
		this.estimatedTaskDuration = estimatedTaskDuration;
	}

	public Set<WorkerEntity> getWorkersSet() {
		return workersSet;
	}

	public void setWorkersSet(Set<WorkerEntity> workersSet) {
		this.workersSet = workersSet;
	}

	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
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
