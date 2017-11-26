package com.ksm.robolo.roboloapp.services;

import java.util.List;

import com.ksm.robolo.roboloapp.services.exceptions.TaskServiceException;
import com.ksm.robolo.roboloapp.tos.TaskTO;

public interface TaskService {

	List<TaskTO> findByProjectId(Long projectId);

	void addTask(Long projectId, TaskTO taskTO) throws TaskServiceException;

	void setTaskDone(Long taskId) throws TaskServiceException;
	
	void setTaskInProgress(Long taskId) throws TaskServiceException;
	
	void setTaskToDo(Long taskId) throws TaskServiceException;

	void deleteTask(Long taskId);
}
