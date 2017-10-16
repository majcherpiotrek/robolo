package com.ksm.robolo.roboloapp.services.util.impl;

import com.ksm.robolo.roboloapp.domain.ProjectEntity;
import com.ksm.robolo.roboloapp.services.util.EntityToTOConverter;
import com.ksm.robolo.roboloapp.tos.ProjectStubTO;

import java.util.List;

public class ProjectEntityToStubConverter implements EntityToTOConverter<ProjectStubTO, ProjectEntity>{

    @Override
    public ProjectStubTO convertToTO(ProjectEntity entity) {
        // TODO implement
        return null;
    }

    @Override
    public List<ProjectStubTO> convertListToTOList(List<ProjectEntity> entityList) {
        // TODO implement
        return null;
    }
}
