package com.ksm.robolo.roboloapp.services.impl;

import com.ksm.robolo.roboloapp.domain.ProjectEntity;
import com.ksm.robolo.roboloapp.enums.TaskStatus;
import com.ksm.robolo.roboloapp.repository.ProjectRepository;
import com.ksm.robolo.roboloapp.repository.TaskRepository;
import com.ksm.robolo.roboloapp.services.EstimationService;
import com.ksm.robolo.roboloapp.services.ProjectService;
import com.ksm.robolo.roboloapp.services.util.impl.ProjectEntityToTOConverter;
import com.ksm.robolo.roboloapp.services.util.impl.TaskToTOConverter;
import com.ksm.robolo.roboloapp.tos.ProjectStubTO;
import com.ksm.robolo.roboloapp.tos.ProjectTO;
import com.ksm.robolo.roboloapp.tos.TaskTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    private static final Logger logger = Logger.getLogger(ProjectServiceImpl.class);
    private static final String ERROR_PROJECTS_NOT_FOUND = "No projects found in the database";

    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    private ProjectEntityToTOConverter projectEntityToTOConverter;
    private TaskToTOConverter taskToTOConverter;
    private EstimationService estimationService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository, EstimationService estimationService) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.projectEntityToTOConverter = new ProjectEntityToTOConverter();
        this.taskToTOConverter = new TaskToTOConverter();
        this.estimationService = estimationService;
    }


    @Override
    public List<ProjectTO> getAllProjects() {
        List<ProjectEntity> projectEntityList = projectRepository.findAll();
        Assert.notNull(projectEntityList, ERROR_PROJECTS_NOT_FOUND);
        List<ProjectTO> projectTOList = new LinkedList<>();

        if (!projectEntityList.isEmpty()) {
            for (ProjectEntity projectEntity : projectEntityList) {
                ProjectTO projectTO = projectEntityToTOConverter.convertToTO(projectEntity);
                List<TaskTO> taskTOList = taskToTOConverter.convertListToTOList(taskRepository.findByProject_Id(projectEntity.getId()));
                projectTO.setTaskTOS(taskTOList);
                projectTO.setApproximateEndDate(estimationService.estimateProjectEndDate(projectTO));

                projectTOList.add(projectTO);
            }
        }
        return projectTOList;
    }

    @Override
    public List<ProjectStubTO> getAllProjectsStubs() {
        // TODO implement
        return null;
    }

    @Override
    public List<ProjectStubTO> getAllProjectStubsFromClient(Long clientId) {
        // TODO implemnt
        return null;
    }

    @Override
    public ProjectTO getProject(Long projectId) {
        // TODO implement
        return null;
    }
}
