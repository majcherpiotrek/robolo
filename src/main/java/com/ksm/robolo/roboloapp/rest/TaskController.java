package com.ksm.robolo.roboloapp.rest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ksm.robolo.roboloapp.enums.TaskStatus;
import com.ksm.robolo.roboloapp.services.TaskService;
import com.ksm.robolo.roboloapp.services.exceptions.TaskServiceException;
import com.ksm.robolo.roboloapp.tos.TaskTO;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	private static final Logger logger = Logger.getLogger(TaskController.class);
	
	private TaskService taskService;
	
	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@GetMapping("/all/{projectId}")
	public ResponseEntity< List <List<TaskTO>> > getAllTasksFromProject(@PathVariable Long projectId) {
		List<TaskTO> taskList = taskService.findByProjectId(projectId);
		
		return new ResponseEntity<List<List<TaskTO>>>(sortTasks(taskList), HttpStatus.OK);
	}
	
	@PostMapping("/add/{projectId}")
    public ResponseEntity<List<List<TaskTO>>> addTaskToProject(@PathVariable Long projectId, @RequestBody TaskTO taskTO, Principal principal) {
		try {
			taskService.addTask(projectId, taskTO);
		} catch (TaskServiceException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<TaskTO> taskList = taskService.findByProjectId(projectId);
		
    	return new ResponseEntity<List<List<TaskTO>>>(sortTasks(taskList), HttpStatus.OK);
    }
	
	@PostMapping("/set/done/{projectId}/{taskId}")
	public ResponseEntity<List<List<TaskTO>>> setTaskDone(@PathVariable Long projectId, @PathVariable Long taskId) {
		
		try {
			taskService.setTaskDone(taskId);
		} catch (TaskServiceException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<TaskTO> taskList = taskService.findByProjectId(projectId);
		
    	return new ResponseEntity<List<List<TaskTO>>>(sortTasks(taskList), HttpStatus.OK);
	}
	

	@PostMapping("/set/inprogress/{projectId}/{taskId}")
	public ResponseEntity<List<List<TaskTO>>> setTaskInProgress(@PathVariable Long projectId, @PathVariable Long taskId) {
		
		try {
			taskService.setTaskInProgress(taskId);
		} catch (TaskServiceException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<TaskTO> taskList = taskService.findByProjectId(projectId);
		
    	return new ResponseEntity<List<List<TaskTO>>>(sortTasks(taskList), HttpStatus.OK);
	}
	

	@PostMapping("/set/todo/{projectId}/{taskId}")
	public ResponseEntity<List<List<TaskTO>>> setTaskToDo(@PathVariable Long projectId, @PathVariable Long taskId) {
		
		try {
			taskService.setTaskToDo(taskId);
		} catch (TaskServiceException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<TaskTO> taskList = taskService.findByProjectId(projectId);
		
    	return new ResponseEntity<List<List<TaskTO>>>(sortTasks(taskList), HttpStatus.OK);
	}
	
	@PostMapping("/{projectId}/{taskId}/add-workers")
	public  ResponseEntity<List<List<TaskTO>>> addWorkersToTask(@PathVariable Long projectId, @PathVariable Long taskId, @RequestBody List<Long> workerIdList) {
		try {
			taskService.updateWorkersList(taskId, workerIdList);
		} catch (TaskServiceException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<TaskTO> taskList = taskService.findByProjectId(projectId);
		
    	return new ResponseEntity<List<List<TaskTO>>>(sortTasks(taskList), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{projectId}/{taskId}")
	public ResponseEntity<List<List<TaskTO>>> deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
		taskService.deleteTask(taskId);
		List<TaskTO> taskList = taskService.findByProjectId(projectId);
    	return new ResponseEntity<List<List<TaskTO>>>(sortTasks(taskList), HttpStatus.OK);
	}
	
	private List<List<TaskTO>> sortTasks(List<TaskTO> tasksToSortList) {
		List<List<TaskTO>> resultList = new ArrayList<>(3);
		
		if (tasksToSortList != null && !tasksToSortList.isEmpty()) {
			List<TaskTO> toDoTasks = tasksToSortList.stream()
					.filter(task -> task.getStatus().equals(TaskStatus.TO_DO))
					.collect(Collectors.toList());
			List<TaskTO> inProgressTasks = tasksToSortList.stream()
					.filter(task -> task.getStatus().equals(TaskStatus.IN_PROGRESS))
					.collect(Collectors.toList());
			List<TaskTO> doneTasks = tasksToSortList.stream()
					.filter(task -> task.getStatus().equals(TaskStatus.DONE))
					.collect(Collectors.toList());
			
			resultList.add(toDoTasks);
			resultList.add(inProgressTasks);
			resultList.add(doneTasks);
		}
		
		return resultList;
	}
}
