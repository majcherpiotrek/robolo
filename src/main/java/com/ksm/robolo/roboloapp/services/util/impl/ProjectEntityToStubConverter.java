package com.ksm.robolo.roboloapp.services.util.impl;

import com.ksm.robolo.roboloapp.domain.ProjectEntity;
import com.ksm.robolo.roboloapp.services.util.EntityToTOConverter;
import com.ksm.robolo.roboloapp.tos.ClientTO;
import com.ksm.robolo.roboloapp.tos.ProjectStubTO;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class ProjectEntityToStubConverter implements EntityToTOConverter<ProjectStubTO, ProjectEntity> {

    @Override
    public ProjectStubTO convertToTO(ProjectEntity entity) {
        Assert.notNull(entity, NULL_ARGUMENT_ERROR);
        ProjectStubTO stub = new ProjectStubTO();
        stub.setId(entity.getId());
        stub.setProjectName(entity.getProjectName());
        stub.setStartDate(entity.getStartDate());
        stub.setAddress(entity.getAddress());
        if (entity.getClient() != null) {
        	final ClientTO clientTO = new ClientToTOConverter().convertToTO(entity.getClient());
        	stub.setClientTO(clientTO);
        }
        return stub;
    }

    @Override
    public List<ProjectStubTO> convertListToTOList(List<ProjectEntity> entityList) {
        List<ProjectStubTO> covertList = new ArrayList<>();

        if (entityList != null) {
            for (ProjectEntity projectEntity : entityList) {
                covertList.add(this.convertToTO(projectEntity));
            }
        }

        return covertList;
    }
}
