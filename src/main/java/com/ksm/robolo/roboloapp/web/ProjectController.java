package com.ksm.robolo.roboloapp.web;

import com.ksm.robolo.roboloapp.tos.ProjectStubTO;
import com.ksm.robolo.roboloapp.tos.ProjectTO;
import com.ksm.robolo.roboloapp.services.ProjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@CrossOrigin
public class ProjectController {

    private static final Logger logger = Logger.getLogger(ProjectController.class);
  
    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(path="/all")
    @CrossOrigin
    public @ResponseBody Iterable<ProjectTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping(path="/stubs/all")
    @CrossOrigin
    public @ResponseBody Iterable<ProjectStubTO> getAllProjectStubs() { return projectService.getAllProjectsStubs(); }

    @GetMapping(path = "/{projectId}")
    @CrossOrigin
    public @ResponseBody ProjectTO getProject(@PathVariable String projectId) {
        ProjectTO projectTO = null;
        try {
            Long projectIdLong = Long.valueOf(projectId);
            projectTO = projectService.getProject(projectIdLong);
            if (projectTO == null) {
                logger.info("Could not find the ProjectTO object with id: " + projectIdLong);
            }
        } catch (NumberFormatException e) {
            logger.error("Incorrect path variable: " + projectId);
            e.printStackTrace();
        }
        return  projectTO;
    }
}
