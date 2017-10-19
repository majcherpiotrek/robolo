package com.ksm.robolo.roboloapp.rest.impl;

import com.ksm.robolo.roboloapp.tos.ProjectStubTO;
import com.ksm.robolo.roboloapp.tos.ProjectTO;
import com.ksm.robolo.roboloapp.services.ProjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    
    //TODO @ResponseBody annotation is not necessary when class has the @RestController. Return ResponseEntities, should be:
    /*
    @GetMapping(path="/all")
    @CrossOrigin
    public ResponseEntity<Iterable<ProjectTO>> getAllProjects() {
        Iterable<ProjectTO> projectTOS = projectService.getAllProjects();
        return projectTOS == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(projectTOS, HttpStatus.OK);
    }
    */
    @GetMapping(path = "/all")
    @CrossOrigin
    public @ResponseBody
    Iterable<ProjectTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping(path = "/stubs/all")
    @CrossOrigin
    //TODO @ResponseBody annotation is not necessary when class has the @RestController. Return ResponseEntities
    public @ResponseBody
    Iterable<ProjectStubTO> getAllProjectStubs() {
        return projectService.getAllProjectsStubs();
    }

    @GetMapping(path = "/{projectId}")
    @CrossOrigin
    //TODO @ResponseBody annotation is not necessary when class has the @RestController. Return ResponseEntities
    public @ResponseBody
    ProjectTO getProject(@PathVariable String projectId) {
        ProjectTO projectTO = null;
        try {
            Long projectIdLong = Long.valueOf(projectId);
            projectTO = projectService.getProject(projectIdLong);
            if (projectTO == null) {
                //TODO should return HttpStatus.NOT_FOUND
            // example: https://stackoverflow.com/questions/22536059/how-to-return-not-found-status-from-spring-controller
                logger.info("Could not find the ProjectTO object with id: " + projectIdLong);
            }
        } catch (NumberFormatException e) {
            logger.error("Incorrect path variable: " + projectId);
            e.printStackTrace();
        }
        return projectTO;
    }


    @GetMapping(path = "/byclient/{clientId}")
    @CrossOrigin
    //TODO @ResponseBody annotation is not necessary when class has the @RestController. Return ResponseEntities
    public @ResponseBody
    Iterable<ProjectStubTO> getAllProjectsStubForClientId(@PathVariable String clientId) {
        List<ProjectStubTO> fromClientList = null;

        try {
            Long clientIdLong = Long.valueOf(clientId);
            fromClientList = projectService.getAllProjectStubsFromClient(clientIdLong);
            if (fromClientList == null) {
                //TODO should return HttpStatus.NOT_FOUND
            // example: https://stackoverflow.com/questions/22536059/how-to-return-not-found-status-from-spring-controller
                logger.info("Could not find the ProjectTO object with id: " + clientIdLong);
            }
        } catch (NumberFormatException e) {
            //TODO should return HttpStatus.BAD_REQUEST
            // example: https://stackoverflow.com/questions/1364527/http-status-code-for-bad-data
            logger.error("Incorrect path variable: " + clientId);
            e.printStackTrace();
        }

        return fromClientList;

    }


}
