package com.ksm.robolo.roboloapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ksm.robolo.roboloapp.domain.UserEntity;
import com.ksm.robolo.roboloapp.domain.WorkerEntity;
import com.ksm.robolo.roboloapp.repository.UserRepository;
import com.ksm.robolo.roboloapp.repository.WorkerRepository;
import com.ksm.robolo.roboloapp.services.WorkerService;
import com.ksm.robolo.roboloapp.services.exceptions.ExceptionUnwrapper;
import com.ksm.robolo.roboloapp.services.exceptions.WorkerServiceException;
import com.ksm.robolo.roboloapp.services.util.EntityToTOConverter;
import com.ksm.robolo.roboloapp.services.util.impl.WorkerToTOConverter;
import com.ksm.robolo.roboloapp.tos.WorkerTO;

@Service
public class WorkerServiceImpl implements WorkerService {

	private static final String NULL_WORKER_ERROR = "Worker cannot be null";
	private WorkerRepository workerRepository;
	private UserRepository userRepository;
	private ExceptionUnwrapper exceptionUnwrapper;
	private EntityToTOConverter<WorkerTO, WorkerEntity> converter;
	
    @Autowired
    public WorkerServiceImpl(
    		WorkerRepository workerRepository, 
    		UserRepository userRepository,
    		ExceptionUnwrapper exceptionUnwrapper) {
        this.workerRepository = workerRepository;
        this.userRepository = userRepository;
        this.exceptionUnwrapper = exceptionUnwrapper;
        this.converter = new WorkerToTOConverter();
    }

	@Override
	public void addWorker(WorkerTO workerTO, String username) throws WorkerServiceException {
		try {
			WorkerEntity worker = createWorkerEntityFromTO(workerTO, userRepository.findByUsername(username));
			workerRepository.saveAndFlush(worker);
		} catch(Exception e) {
			throw new WorkerServiceException(exceptionUnwrapper.getExceptionMessage(e));
		}
	}

	private WorkerEntity createWorkerEntityFromTO(WorkerTO workerTO, UserEntity user) {
		Assert.notNull(workerTO, NULL_WORKER_ERROR);
		
		WorkerEntity worker = new WorkerEntity();
		worker.setName(workerTO.getName());
		worker.setSurname(workerTO.getSurname());
		worker.setTelephoneNumbers(workerTO.getTelephoneNumbers());
		worker.setUserEntity(user);
		
		return worker;
	}

	@Override
	public List<WorkerTO> getAllWorkers(String username) {
		UserEntity user = userRepository.findByUsername(username);
		return converter.convertListToTOList(workerRepository.findAllByUserEntityId(user.getId()));
	}
}
