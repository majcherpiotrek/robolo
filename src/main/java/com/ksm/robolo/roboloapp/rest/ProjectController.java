package com.ksm.robolo.roboloapp.rest;

import com.ksm.robolo.roboloapp.services.ProjectService;
import com.ksm.robolo.roboloapp.tos.ProjectStubTO;
import com.ksm.robolo.roboloapp.tos.ProjectTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private static final Logger logger = Logger.getLogger(ProjectController.class);

    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping(path = "/all")
    public ResponseEntity<Iterable<ProjectTO>> getAllProjects(Principal principal) {
    	// TODO return only projects that belong to the logged in user (principal)
        Iterable<ProjectTO> projectTOS = projectService.getAllProjects();
        return projectTOS == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(projectTOS, HttpStatus.OK);
    }


    @GetMapping(path = "/stubs/all")
    public ResponseEntity<Iterable<ProjectStubTO>> getAllProjectStubs(Principal principal) {
    	// TODO return only projects that belong to the logged in user (principal)
        final Iterable<ProjectStubTO> allProjectsStubs = projectService.getAllProjectsStubs();
        return allProjectsStubs == null ?
                new ResponseEntity<Iterable<ProjectStubTO>>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(allProjectsStubs, HttpStatus.OK);

    }

    @GetMapping(path = "/{projectId}")
    public ResponseEntity<ProjectTO> getProject(@PathVariable String projectId, Principal principal) {
    	// TODO return only projects that belong to the logged in user (principal)
    	ProjectTO projectTO = null;
        Long projectIdLong = Long.valueOf(projectId);
        projectTO = projectService.getProject(projectIdLong);


        return projectTO == null ? new ResponseEntity<ProjectTO>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<ProjectTO>(projectTO, HttpStatus.OK);
    }


    @GetMapping(path = "/byclient/{clientId}")
    public ResponseEntity<Iterable<ProjectStubTO>> getAllProjectsStubForClientId(@PathVariable String clientId, Principal principal) {
    	// TODO return only projects that belong to the logged in user (principal)
    	List<ProjectStubTO> fromClientList = null;
        Long clientIdLong = Long.valueOf(clientId);
        fromClientList = projectService.getAllProjectStubsFromClient(clientIdLong);

        return fromClientList == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(fromClientList, HttpStatus.OK);


    }


}
