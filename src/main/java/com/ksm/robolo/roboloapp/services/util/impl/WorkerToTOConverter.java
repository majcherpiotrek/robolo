package com.ksm.robolo.roboloapp.services.util.impl;

import com.ksm.robolo.roboloapp.domain.WorkerEntity;
import com.ksm.robolo.roboloapp.services.util.EntityToTOConverter;
import com.ksm.robolo.roboloapp.tos.WorkerTO;
import org.springframework.util.Assert;

import java.util.List;

public class WorkerToTOConverter implements EntityToTOConverter<WorkerTO, WorkerEntity>{

    @Override
    public WorkerTO convertToTO(WorkerEntity entity) {
        Assert.notNull(entity, NULL_ARGUMENT_ERROR);
        WorkerTO workerTO = new WorkerTO();
        workerTO.setId(entity.getId());
        workerTO.setName(entity.getName());
        workerTO.setSurname(entity.getSurname());
        workerTO.setTelephoneNumbers(entity.getTelephoneNumbers());
        return workerTO;
    }

    @Override
    public List<WorkerTO> convertListToTOList(List<WorkerEntity> entityList) {
        // TODO implement
        return null;
    }
}
