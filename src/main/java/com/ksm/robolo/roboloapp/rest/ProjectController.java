package com.ksm.robolo.roboloapp.rest;

import com.ksm.robolo.roboloapp.services.ProjectService;
import com.ksm.robolo.roboloapp.services.exceptions.ProjectServiceException;
import com.ksm.robolo.roboloapp.tos.ClientTO;
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
    private static final String PROJECT_UPDATED_MSG = "Project has been updated";
    
    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping(path = "/all")
    public ResponseEntity<List<ProjectTO>> getAllProjects(Principal principal) {
        List<ProjectTO> projectTOS = projectService.getAllProjects(principal.getName());
        return new ResponseEntity<>(projectTOS, HttpStatus.OK);
    }


    @GetMapping(path = "/stubs/all", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectStubTO>> getAllProjectStubs(Principal principal) {
        final List<ProjectStubTO> allProjectsStubs = projectService.getAllProjectsStubs(principal.getName());
        return new ResponseEntity<>(allProjectsStubs, HttpStatus.OK);
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
    public ResponseEntity<List<ProjectStubTO>> getAllProjectsStubForClientId(@PathVariable String clientId, Principal principal) {
    	List<ProjectStubTO> fromClientList = null;
        Long clientIdLong = Long.valueOf(clientId);
        fromClientList = projectService.getAllProjectStubsFromClient(principal.getName(), clientIdLong);

        return new ResponseEntity<>(fromClientList, HttpStatus.OK);
    }
    
    @PostMapping("/edit/{projectId}")
    public ResponseEntity<List<ProjectStubTO>> editProject(@PathVariable Long projectId, @RequestBody ProjectTO projectTO, Principal principal) {
		try {
			projectService.editProject(projectId, projectTO);
			
	    	logger.info(PROJECT_UPDATED_MSG);
		} catch (ProjectServiceException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		final List<ProjectStubTO> allProjectsStubs = projectService.getAllProjectsStubs(principal.getName());
        return new ResponseEntity<>(allProjectsStubs, HttpStatus.OK);
    }
    
    @PostMapping("/update-client/{projectId}")
    public ResponseEntity<ProjectTO> updateClientInProject(@PathVariable Long projectId, @RequestBody ClientTO clientTO, Principal principal) {
    	try {
    		projectService.updateClient(projectId, clientTO);
    	} catch (ProjectServiceException e) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	
    	return new ResponseEntity<ProjectTO>(projectService.getProject(principal.getName(), projectId), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<List<ProjectStubTO>> addProject(@RequestBody ProjectTO projectTO, Principal principal) {
    	String username = principal.getName();
    	try {
			projectService.addProject(username, projectTO);
			
	    	logger.info(PROJECT_ADDED_MSG);
		} catch (ProjectServiceException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    	final List<ProjectStubTO> allProjectsStubs = projectService.getAllProjectsStubs(username);
        return new ResponseEntity<>(allProjectsStubs, HttpStatus.OK);
    }

}
