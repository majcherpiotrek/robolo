package com.ksm.robolo.roboloapp.services;

import com.ksm.robolo.roboloapp.services.exceptions.ProjectServiceException;
import com.ksm.robolo.roboloapp.tos.ClientTO;
import com.ksm.robolo.roboloapp.tos.ProjectStubTO;
import com.ksm.robolo.roboloapp.tos.ProjectTO;

import java.util.List;

public interface ProjectService {

    List<ProjectTO> getAllProjects(String username);

    List<ProjectStubTO> getAllProjectsStubs(String username);

    List<ProjectStubTO> getAllProjectStubsFromClient(String username, Long clientId);

    ProjectTO getProject(String username, Long projectId);

	void addProject(String username, ProjectTO projectTO) throws ProjectServiceException;

	void editProject(Long projectId, ProjectTO projectTO) throws ProjectServiceException;

	void updateClient(Long projectId, ClientTO clientTO) throws ProjectServiceException;
}
