package com.ksm.robolo.roboloapp.rest;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ksm.robolo.roboloapp.services.WorkerService;
import com.ksm.robolo.roboloapp.services.exceptions.WorkerServiceException;
import com.ksm.robolo.roboloapp.tos.WorkerTO;


@RestController
@RequestMapping("/workers")
public class WorkerController {

    private WorkerService workerService;

    @Autowired
    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }
    
    @PostMapping("/add")
	public ResponseEntity<List<WorkerTO>> addClient(@RequestBody WorkerTO workerTO, Principal principal) {
		List<WorkerTO> updatedList = new LinkedList<>();
		try {
			workerService.addWorker(workerTO, principal.getName());
			updatedList = workerService.getAllWorkers(principal.getName());
		} catch (WorkerServiceException e) {
			e.printStackTrace();
			return new ResponseEntity<List<WorkerTO>>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<List<WorkerTO>>(updatedList, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<WorkerTO>> getAllWorkers(Principal principal) {
		return new ResponseEntity<List<WorkerTO>>(workerService.getAllWorkers(principal.getName()), HttpStatus.OK);
	}
}

