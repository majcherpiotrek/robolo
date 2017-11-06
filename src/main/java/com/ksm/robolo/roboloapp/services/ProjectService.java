package com.ksm.robolo.roboloapp.services;

import com.ksm.robolo.roboloapp.tos.ProjectStubTO;
import com.ksm.robolo.roboloapp.tos.ProjectTO;

import java.util.List;

public interface ProjectService {

    List<ProjectTO> getAllProjects(String username);

    List<ProjectStubTO> getAllProjectsStubs(String username);

    List<ProjectStubTO> getAllProjectStubsFromClient(String username, Long clientId);

    ProjectTO getProject(String username, Long projectId);
}
