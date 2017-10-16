package com.ksm.robolo.roboloapp.services.util.impl;


import com.ksm.robolo.roboloapp.domain.TaskEntity;
import com.ksm.robolo.roboloapp.domain.WorkerEntity;
import com.ksm.robolo.roboloapp.services.util.EntityToTOConverter;
import com.ksm.robolo.roboloapp.tos.TaskTO;
import com.ksm.robolo.roboloapp.tos.WorkerTO;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

public class TaskToTOConverter implements EntityToTOConverter<TaskTO, TaskEntity>{

    private WorkerToTOConverter workerConverter;

    public TaskToTOConverter() {
        workerConverter = new WorkerToTOConverter();
    }
    @Override
    public TaskTO convertToTO(TaskEntity entity) {
        TaskTO taskTO = new TaskTO();
        taskTO.setId(entity.getId());
        taskTO.setCreationDate(entity.getCreationDate());
        taskTO.setDescription(entity.getDescription());
        taskTO.setEstimatedTaskDuration(entity.getEstimatedTaskDuration());
        taskTO.setStartDate(entity.getStartDate());
        taskTO.setStatus(entity.getStatus());

        taskTO.setTaskItems(entity.getTaskItems());
        taskTO.setWorkers(getWorkerTOsFromTaskEntity(entity));

        return taskTO;
    }

    @Override
    public List<TaskTO> convertListToTOList(List<TaskEntity> entityList) {
        Assert.notNull(entityList, NULL_ARGUMENT_ERROR);
        List<TaskTO> taskTOList = new LinkedList<>();
        for (TaskEntity taskEntity : entityList) {
            taskTOList.add(convertToTO(taskEntity));
        }
        return taskTOList;
    }

    private List<WorkerTO> getWorkerTOsFromTaskEntity(TaskEntity entity) {
        List<WorkerTO> workerTOS = new LinkedList<>();
        for (WorkerEntity worker : entity.getWorkers()) {
            WorkerTO workerTO = workerConverter.convertToTO(worker);
            workerTOS.add(workerTO);
        }
        return workerTOS;
    }
}
