package com.ksm.robolo.roboloapp.services;

import java.util.List;

import com.ksm.robolo.roboloapp.services.exceptions.WorkerServiceException;
import com.ksm.robolo.roboloapp.tos.WorkerTO;

public interface WorkerService {

	void addWorker(WorkerTO workerTO, String username) throws WorkerServiceException;

	List<WorkerTO> getAllWorkers(String username);
}
