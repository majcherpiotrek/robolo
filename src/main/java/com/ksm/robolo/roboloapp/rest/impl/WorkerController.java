package com.ksm.robolo.roboloapp.rest.impl;

import com.ksm.robolo.roboloapp.domain.WorkerEntity;
import com.ksm.robolo.roboloapp.services.impl.WorkerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/workers")
@CrossOrigin
public class WorkerController {

    private WorkerServiceImpl workerService;

    @Autowired
    public WorkerController(WorkerServiceImpl workerService) {
        this.workerService = workerService;
    }
}
