package com.ksm.robolo.roboloapp;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ksm.robolo.roboloapp.domain.AddressEntity;
import com.ksm.robolo.roboloapp.domain.ClientEntity;
import com.ksm.robolo.roboloapp.domain.ProjectEntity;
import com.ksm.robolo.roboloapp.domain.UserEntity;
import com.ksm.robolo.roboloapp.domain.WorkerEntity;
import com.ksm.robolo.roboloapp.repository.AddressRepository;
import com.ksm.robolo.roboloapp.repository.ClientRepository;
import com.ksm.robolo.roboloapp.repository.ProjectRepository;
import com.ksm.robolo.roboloapp.repository.TaskItemRepository;
import com.ksm.robolo.roboloapp.repository.TaskRepository;
import com.ksm.robolo.roboloapp.repository.WorkerRepository;
import com.ksm.robolo.roboloapp.services.UserService;
import com.ksm.robolo.roboloapp.services.exceptions.RegistrationException;
import com.ksm.robolo.roboloapp.tos.UserTO;

@Component
public class DevelopmentDataLoader implements ApplicationRunner {
	
	private static final Logger logger = Logger.getLogger(DevelopmentDataLoader.class);
	
	private UserService userService;
	private ClientRepository clientRepository;
	private AddressRepository addressRepository;
	private ProjectRepository projectRepository;
	private WorkerRepository workerRepository;
	
	@Autowired
	public DevelopmentDataLoader(
			UserService userService, 
			ProjectRepository projectRepository,
			ClientRepository clientRepository,
			WorkerRepository workerRepository,
			TaskRepository taskRepository,
			TaskItemRepository taskItemRepository,
			AddressRepository addressRepository) {
		
		this.userService = userService;
		this.clientRepository = clientRepository;
		this.addressRepository = addressRepository;
		this.projectRepository = projectRepository;
		this.workerRepository = workerRepository;
	}
	
	private Iterable<WorkerEntity> createAndSaveWorkersList(UserEntity user) {
		List<WorkerEntity> workers = new LinkedList<>();
		
		for (int i = 0; i<10; i++) {
			WorkerEntity workerEntity = new WorkerEntity();
			workerEntity.setName("Robol" + i);
			workerEntity.setSurname("Robolowski" + i);
			workerEntity.setTelephoneNumbers(Arrays.asList("11111111" + i));
			workerEntity.setUserEntity(user);
			workers.add(workerEntity);
		}
		return workerRepository.save(workers);
	}

	private void createAndSaveProjectEntity(
			AddressEntity address, 
			ClientEntity client, 
			UserEntity user,
			List<WorkerEntity> workers) {
		ProjectEntity projectEntity = new ProjectEntity();
		projectEntity.setAddress(address);
		projectEntity.setClient(client);
		projectEntity.setProjectName("Projekt testowy");
		projectEntity.setStartDate(new Date());
		projectEntity.setUserEntity(user);
		projectEntity.setWorkers(workers);
		projectRepository.save(projectEntity);
	}
	private AddressEntity createAndSaveAddressEntity() {
		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setApartmentNumber("1");
		addressEntity.setCity("Wroclaw");
		addressEntity.setCountry("Polsza");
		addressEntity.setHouseNumber("69");
		addressEntity.setPostCode("11-234");
		addressEntity.setStreet("Ulica");
		return addressRepository.save(addressEntity);
	}
	
	private ClientEntity createAndSaveClientEntity(UserEntity user) {
		ClientEntity clientEntity = new ClientEntity();
		clientEntity.setUserEntity(user);
		clientEntity.setName("Jan");
		clientEntity.setEmailAddress("jakis@gmail.com");
		clientEntity.setSurname("Kowalski");
		clientEntity.setTelephoneNumbers(Arrays.asList("123456789"));
		return clientRepository.save(clientEntity);
	}
	
	private UserTO createAndRegisterTestUser() throws RegistrationException {
		UserTO userTO = new UserTO();
		userTO.setName("Piotrek");
		userTO.setSurname("Majcher");
		userTO.setEmail("piotr.majcher94@gmail.com");
		userTO.setPassword("password");
		userTO.setMatchingPassword("password");
		userTO.setUsername("username");
		
		userService.registerUser(userTO);
		String token = UUID.randomUUID().toString();
        userService.createVerificationToken(token, userTO);
        userService.confirmUser(token);
        return userTO;
	}

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		try {
			UserTO userTO = createAndRegisterTestUser();
			UserEntity user = userService.findByEmail(userTO.getEmail());
			
			ClientEntity client = createAndSaveClientEntity(user);
			AddressEntity address = createAndSaveAddressEntity();
			List<WorkerEntity> workers = (List<WorkerEntity>) createAndSaveWorkersList(user);
			createAndSaveProjectEntity(address, client, user, workers);
		} catch (RegistrationException e) {
			logger.error("Failed to create test user");
		}
		
	}

}
