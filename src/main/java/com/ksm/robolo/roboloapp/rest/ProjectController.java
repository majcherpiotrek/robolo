package com.ksm.robolo.roboloapp.rest;

import com.ksm.robolo.roboloapp.services.ProjectService;
import com.ksm.robolo.roboloapp.services.exceptions.ProjectServiceException;
import com.ksm.robolo.roboloapp.tos.ProjectStubTO;
import com.ksm.robolo.roboloapp.tos.ProjectTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private static final Logger logger = Logger.getLogger(ProjectController.class);
    private static final String PROJECT_ADDED_MSG = "New project has been created";
    
    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping(path = "/all")
    public ResponseEntity<Iterable<ProjectTO>> getAllProjects(Principal principal) {
        Iterable<ProjectTO> projectTOS = projectService.getAllProjects(principal.getName());
        return projectTOS == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(projectTOS, HttpStatus.OK);
    }


    @GetMapping(path = "/stubs/all", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<ProjectStubTO>> getAllProjectStubs(Principal principal) {
        final Iterable<ProjectStubTO> allProjectsStubs = projectService.getAllProjectsStubs(principal.getName());
        return allProjectsStubs == null ?
                new ResponseEntity<Iterable<ProjectStubTO>>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(allProjectsStubs, HttpStatus.OK);

    }

    @GetMapping(path = "/{projectId}")
    public ResponseEntity<ProjectTO> getProject(@PathVariable String projectId, Principal principal) {
    	ProjectTO projectTO = null;
        Long projectIdLong = Long.valueOf(projectId);
        projectTO = projectService.getProject(principal.getName(), projectIdLong);


        return projectTO == null ? new ResponseEntity<ProjectTO>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<ProjectTO>(projectTO, HttpStatus.OK);
    }


    @GetMapping(path = "/byclient/{clientId}")
    public ResponseEntity<Iterable<ProjectStubTO>> getAllProjectsStubForClientId(@PathVariable String clientId, Principal principal) {
    	List<ProjectStubTO> fromClientList = null;
        Long clientIdLong = Long.valueOf(clientId);
        fromClientList = projectService.getAllProjectStubsFromClient(principal.getName(), clientIdLong);

        return fromClientList == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(fromClientList, HttpStatus.OK);
    }
    
    @PostMapping("/projects/add")
    public ResponseEntity<String> addProject(@RequestBody ProjectTO projectTO, Principal principal) {
    	try {
			projectService.addProject(principal.getName(), projectTO);
		} catch (ProjectServiceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    	
    	return new ResponseEntity<>(PROJECT_ADDED_MSG, HttpStatus.OK);
    }

}
