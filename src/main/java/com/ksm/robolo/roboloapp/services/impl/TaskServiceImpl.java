package com.ksm.robolo.roboloapp.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksm.robolo.roboloapp.domain.ProjectEntity;
import com.ksm.robolo.roboloapp.domain.TaskEntity;
import com.ksm.robolo.roboloapp.domain.WorkerEntity;
import com.ksm.robolo.roboloapp.enums.TaskStatus;
import com.ksm.robolo.roboloapp.repository.ProjectRepository;
import com.ksm.robolo.roboloapp.repository.TaskRepository;
import com.ksm.robolo.roboloapp.repository.WorkerRepository;
import com.ksm.robolo.roboloapp.services.TaskService;
import com.ksm.robolo.roboloapp.services.WorkerService;
import com.ksm.robolo.roboloapp.services.exceptions.ExceptionUnwrapper;
import com.ksm.robolo.roboloapp.services.exceptions.TaskServiceException;
import com.ksm.robolo.roboloapp.services.util.impl.TaskToTOConverter;
import com.ksm.robolo.roboloapp.tos.TaskTO;

@Service
public class TaskServiceImpl implements TaskService {

	private TaskRepository taskRepository;
	private ProjectRepository projectRepository;
	private TaskToTOConverter taskToTOConverter;
	private ExceptionUnwrapper exceptionUnwrapper;
	private WorkerRepository workerRepository;
	
	@Autowired
	public TaskServiceImpl(
			TaskRepository taskRepository, 
			ProjectRepository projectRepository, 
			TaskToTOConverter taskToTOConverter, 
			ExceptionUnwrapper exceptionUnwrapper,
			WorkerRepository workerRepository) {
		this.taskRepository = taskRepository;
		this.projectRepository = projectRepository;
		this.taskToTOConverter = taskToTOConverter;
		this.exceptionUnwrapper = exceptionUnwrapper;
		this.workerRepository = workerRepository;
	}

	@Override
	public List<TaskTO> findByProjectId(Long projectId) {
		List<TaskEntity> taskList = taskRepository.findByProject_Id(projectId);
		return taskToTOConverter.convertListToTOList(taskList);
	}

	@Override
	public void addTask(Long projectId, TaskTO taskTO) throws TaskServiceException {
		ProjectEntity projectEntity = projectRepository.findById(projectId);
		
		if (projectEntity == null) {
			throw new TaskServiceException("Project not found!");
		}
		
		try {
			TaskEntity task = new TaskEntity();
			task.setProject(projectEntity);
			task.setCreationDate(new Date());
			task.setDescription(taskTO.getDescription());
			task.setEstimatedTaskDurationHours(taskTO.getEstimatedTaskDurationHours());
			task.setStatus(TaskStatus.TO_DO);
			task.setUserEntity(projectEntity.getUserEntity());
			task.setTaskItems(new ArrayList<>());
			task.setWorkers(new ArrayList<>());
			
			taskRepository.saveAndFlush(task);
		} catch (Exception e) {
			String errorMessage = exceptionUnwrapper.getExceptionMessage(e);
			throw new TaskServiceException(errorMessage);
		}
	}

	@Override
	public void setTaskDone(Long taskId) throws TaskServiceException {
		TaskEntity task = taskRepository.findById(taskId);
		
		if (task == null) {
			throw new TaskServiceException("Task not found!");
		}
		
		task.setStatus(TaskStatus.DONE);
		taskRepository.saveAndFlush(task);
		
	}

	@Override
	public void setTaskInProgress(Long taskId) throws TaskServiceException {
		TaskEntity task = taskRepository.findById(taskId);
		
		if (task == null) {
			throw new TaskServiceException("Task not found!");
		}
		
		task.setStatus(TaskStatus.IN_PROGRESS);
		taskRepository.saveAndFlush(task);
	}

	@Override
	public void setTaskToDo(Long taskId) throws TaskServiceException {
		TaskEntity task = taskRepository.findById(taskId);
		
		if (task == null) {
			throw new TaskServiceException("Task not found!");
		}
		
		task.setStatus(TaskStatus.TO_DO);
		taskRepository.saveAndFlush(task);
	}

	@Override
	public void deleteTask(Long taskId) {
		taskRepository.deleteById(taskId);
	}

	@Override
	public void updateWorkersList(Long taskId, List<Long> workerIdList) throws TaskServiceException {
		TaskEntity task = taskRepository.findById(taskId);
		if (task == null) {
			throw new TaskServiceException("Task not found!");
		}
		
		List<WorkerEntity> workers = new LinkedList<>();
		for (Long workerId : workerIdList) {
			workers.add(workerRepository.findById(workerId));
		}
		
		task.setWorkers(workers);
		taskRepository.save(task);
	}
}
