package com.ksm.robolo.roboloapp.services;

import com.ksm.robolo.roboloapp.tos.ProjectStubTO;
import com.ksm.robolo.roboloapp.tos.ProjectTO;
import com.ksm.robolo.roboloapp.tos.TaskTO;
import com.ksm.robolo.roboloapp.tos.WorkerTO;
import com.sun.jmx.snmp.tasks.Task;

import java.util.List;

public interface ProjectService {

    List<ProjectTO> getAllProjects();

    List<ProjectStubTO> getAllProjectsStubs();

    List<ProjectStubTO> getAllProjectStubsFromClient(Long clientId);

    ProjectTO getProject(Long projectId);
}
