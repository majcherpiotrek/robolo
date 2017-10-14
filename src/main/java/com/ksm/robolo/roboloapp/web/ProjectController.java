package com.ksm.robolo.roboloapp.web;

import com.ksm.robolo.roboloapp.models.Project;
import com.ksm.robolo.roboloapp.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@CrossOrigin
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(path="/all")
    @CrossOrigin
    public @ResponseBody Iterable<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
}
