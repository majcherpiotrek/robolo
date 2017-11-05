package com.ksm.robolo.roboloapp.services.impl;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksm.robolo.roboloapp.repository.AddressRepository;
import com.ksm.robolo.roboloapp.repository.ClientRepository;
import com.ksm.robolo.roboloapp.repository.ProjectRepository;
import com.ksm.robolo.roboloapp.repository.TaskItemRepository;
import com.ksm.robolo.roboloapp.repository.TaskRepository;
import com.ksm.robolo.roboloapp.repository.WorkerRepository;
import com.ksm.robolo.roboloapp.services.UserService;
import com.ksm.robolo.roboloapp.services.exceptions.RegistrationException;
import com.ksm.robolo.roboloapp.tos.UserTO;

@Service
public class DevelopmentDataLoader {
	
	private static final Logger logger = Logger.getLogger(DevelopmentDataLoader.class);
	
	private UserService userService;
	
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
		
		try {
			createTestUser();
		} catch (RegistrationException e) {
			logger.error("Failed to create test user");
		}	
	}
	
	private void createTestUser() throws RegistrationException {
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
	}

}
