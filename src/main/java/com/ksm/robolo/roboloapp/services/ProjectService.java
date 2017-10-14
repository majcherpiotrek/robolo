package com.ksm.robolo.roboloapp.services;

import com.ksm.robolo.roboloapp.tos.ProjectStubTO;
import com.ksm.robolo.roboloapp.tos.ProjectTO;

import java.util.List;

public interface ProjectService {

    List<ProjectTO> getAllProjects();

    List<ProjectStubTO> getAllProjectsStubs();

    List<ProjectStubTO> getAllProjectStubsFromClient(Long clientId);

    ProjectTO getProject(Long projectId);
}
