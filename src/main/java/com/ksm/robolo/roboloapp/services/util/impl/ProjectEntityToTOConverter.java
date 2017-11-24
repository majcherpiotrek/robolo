package com.ksm.robolo.roboloapp.services.util.impl;

import com.ksm.robolo.roboloapp.domain.ProjectEntity;
import com.ksm.robolo.roboloapp.domain.TaskEntity;
import com.ksm.robolo.roboloapp.domain.WorkerEntity;
import com.ksm.robolo.roboloapp.services.util.EntityToTOConverter;
import com.ksm.robolo.roboloapp.tos.*;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

public class ProjectEntityToTOConverter implements EntityToTOConverter<ProjectTO, ProjectEntity>{

    private ClientToTOConverter clientConverter;
    private WorkerToTOConverter workerConverter;

    public ProjectEntityToTOConverter() {
        clientConverter = new ClientToTOConverter();
        workerConverter = new WorkerToTOConverter();
    }
    /**
     * Converts the ProjectEntity to ProjectTO. Leaves the TO's fields
     * that don't exist in the entity null
     * @param entity the ProjectEntity object to be converted
     * @return the ProjectTO object
     */
    @Override
    public ProjectTO convertToTO(ProjectEntity entity) {
        Assert.notNull(entity, NULL_ARGUMENT_ERROR);
        ProjectTO projectTO = new ProjectTO();
        projectTO.setId(entity.getId());
        projectTO.setProjectName(entity.getProjectName());
        projectTO.setStartDate(entity.getStartDate());
        projectTO.setAddress(entity.getAddress());
        
        if (entity.getClient() != null) {
        	ClientTO clientTO = clientConverter.convertToTO(entity.getClient());
            projectTO.setClientTO(clientTO);
        }
        
        projectTO.setWorkerTOS(getWorkerTOsFromProjectEntity(entity));
        projectTO.setTaskTOS(new LinkedList<>());

        return projectTO;
    }

    @Override
    public List<ProjectTO> convertListToTOList(List<ProjectEntity> entityList) {
        Assert.notNull(entityList, NULL_ARGUMENT_ERROR);
        List<ProjectTO> projectTOList = new LinkedList<>();
        for (ProjectEntity projectEntity : entityList) {
            projectTOList.add(convertToTO(projectEntity));
        }
        return projectTOList;
    }

    private List<WorkerTO> getWorkerTOsFromProjectEntity(ProjectEntity entity) {
        List<WorkerTO> workerTOS = new LinkedList<>();
        if (entity.getWorkers() != null) {
            for (WorkerEntity worker : entity.getWorkers()) {
                WorkerTO workerTO = workerConverter.convertToTO(worker);
                workerTOS.add(workerTO);
            }
        }
        return workerTOS;
    }
}
