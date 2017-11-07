package com.ksm.robolo.roboloapp.services.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksm.robolo.roboloapp.domain.ProjectEntity;
import com.ksm.robolo.roboloapp.repository.ProjectRepository;
import com.ksm.robolo.roboloapp.repository.TaskRepository;
import com.ksm.robolo.roboloapp.services.EstimationService;
import com.ksm.robolo.roboloapp.services.ProjectService;
import com.ksm.robolo.roboloapp.services.UserService;
import com.ksm.robolo.roboloapp.services.util.impl.ProjectEntityToStubConverter;
import com.ksm.robolo.roboloapp.services.util.impl.ProjectEntityToTOConverter;
import com.ksm.robolo.roboloapp.services.util.impl.TaskToTOConverter;
import com.ksm.robolo.roboloapp.tos.ProjectStubTO;
import com.ksm.robolo.roboloapp.tos.ProjectTO;
import com.ksm.robolo.roboloapp.tos.TaskTO;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger logger = Logger.getLogger(ProjectServiceImpl.class);

    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    private ProjectEntityToTOConverter projectEntityToTOConverter;
    private ProjectEntityToStubConverter projectEntityToStubConverter;
    private TaskToTOConverter taskToTOConverter;
    private EstimationService estimationService;
    private UserService userService;
    
    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository, UserService userService, EstimationService estimationService) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.projectEntityToTOConverter = new ProjectEntityToTOConverter();
        this.projectEntityToStubConverter = new ProjectEntityToStubConverter();
        this.taskToTOConverter = new TaskToTOConverter();
        this.estimationService = estimationService;
    }


    @Override
    public List<ProjectTO> getAllProjects(String username) {
    	List<ProjectTO> projectTOList = new LinkedList<>();
    	UUID userId = userService.getUserId(username);
    	
    	if (userId != null) {
    		List<ProjectEntity> projectEntityList = findAllProjectEntitiesByUsername(username);
        
    		if (projectEntityList != null) {
                for (ProjectEntity projectEntity : projectEntityList) {
                    ProjectTO projectTO = projectEntityToTOConverter.convertToTO(projectEntity);
                    List<TaskTO> taskTOList = taskToTOConverter.convertListToTOList(taskRepository.findByProject_Id(projectEntity.getId()));
                    projectTO.setTaskTOS(taskTOList);
                    projectTO.setApproximateEndDate(estimationService.estimateProjectEndDate(projectTO));

                    projectTOList.add(projectTO);
                }
            }
    	}
        
        return projectTOList;
    }

    @Override
    public List<ProjectStubTO> getAllProjectsStubs(String username) {
    	UUID userId = userService.getUserId(username);
    	List<ProjectStubTO> projectStubs = new ArrayList<>();
    	
    	if (userId != null) {
    		List<ProjectEntity> projectEntityList = findAllProjectEntitiesByUsername(username);
    		if (projectEntityList != null) {
    			projectStubs = projectEntityToStubConverter.convertListToTOList(projectEntityList);
    		} 
    	}
     
        return projectStubs;
    }

    @Override
    public List<ProjectStubTO> getAllProjectStubsFromClient(String username, Long clientId) {
    	UUID userId = userService.getUserId(username);
    	List<ProjectStubTO> projectStubsWithClientId = new ArrayList<>();
    	
    	if (userId != null) {
    		List<ProjectEntity> projectEntityList = findAllProjectEntitesByUsernameAndClientId(username, clientId);
    		if (projectEntityList != null) {
    			projectStubsWithClientId = projectEntityToStubConverter.convertListToTOList(projectEntityList);
    		} 
    	}
        
        return projectStubsWithClientId;
    }

    @Override
    public ProjectTO getProject(String username, Long projectId) {
    	ProjectTO projectTO = null;
        final ProjectEntity projectEntity = projectRepository.findOne(projectId);
        if (projectEntity != null) {
        	if (projectEntity.getUserEntity().getUsername().equals(username)) {
        		projectTO = projectEntityToTOConverter.convertToTO(projectEntity);
        	}	
        }
        return projectTO;
    }
    
    private List<ProjectEntity> findAllProjectEntitiesByUsername(String username) {
    	return projectRepository.findAll().stream()
				.filter(project -> project.getUserEntity().getUsername().equals(username))
				.collect(Collectors.toList());
    }
    
    private List<ProjectEntity> findAllProjectEntitesByUsernameAndClientId(String username, Long clientId) {
    	return projectRepository.findAll().stream()
    			.filter(project -> 
    					project.getUserEntity().getUsername().equals(username) 
    					&& project.getClient().getId().equals(clientId))
    			.collect(Collectors.toList());
    }
}
