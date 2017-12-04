package com.ksm.robolo.roboloapp.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksm.robolo.roboloapp.domain.ClientEntity;
import com.ksm.robolo.roboloapp.domain.UserEntity;
import com.ksm.robolo.roboloapp.repository.ClientRepository;
import com.ksm.robolo.roboloapp.repository.UserRepository;
import com.ksm.robolo.roboloapp.services.ClientService;
import com.ksm.robolo.roboloapp.services.ProjectService;
import com.ksm.robolo.roboloapp.services.exceptions.ClientServiceException;
import com.ksm.robolo.roboloapp.services.exceptions.ExceptionUnwrapper;
import com.ksm.robolo.roboloapp.services.util.EntityToTOConverter;
import com.ksm.robolo.roboloapp.services.util.impl.ClientToTOConverter;
import com.ksm.robolo.roboloapp.tos.ClientTO;

import org.springframework.util.Assert;


@Service
public class ClientServiceImpl implements ClientService {

	private final static String NULL_CLIENT_ERROR = "Client cannot be null!";
	
	private ClientRepository clientRepository;
	private UserRepository userRepository;
	private ExceptionUnwrapper exceptionUnwrapper;
	private ProjectService projectService;
	private EntityToTOConverter<ClientTO, ClientEntity> converter;
	
	@Autowired
	public ClientServiceImpl(
			ClientRepository clientRepository, 
			UserRepository userRepository, 
			ExceptionUnwrapper exceptionUnwrapper,
			ProjectService projectService
			) {
		this.clientRepository = clientRepository;
		this.userRepository = userRepository;
		this.exceptionUnwrapper = exceptionUnwrapper;
		this.converter = new ClientToTOConverter();
		this.projectService = projectService;
	}

	@Transactional
	@Override
	public void addClient(ClientTO clientTO, String username) throws ClientServiceException {
		try {
			ClientEntity client = createClientEntityFromTO(clientTO,userRepository.findByUsername(username));
			clientRepository.saveAndFlush(client);
		} catch(Exception e) {
			throw new ClientServiceException(exceptionUnwrapper.getExceptionMessage(e));
		}
	}

	@Override
	public List<ClientTO> getAllClients(String username) {
		UserEntity user = userRepository.findByUsername(username);
		return converter.convertListToTOList(clientRepository.findAllByUserEntityId(user.getId()));
	}
	
	@Transactional
	@Override
	public void deleteClient(Long clientId, String username) throws ClientServiceException {
		try {
			ClientEntity clientToDelete = clientRepository.findById(clientId);
			UserEntity user = userRepository.findByUsername(username);
			if (user != null && clientToDelete != null && user.getId().equals(clientToDelete.getUserEntity().getId())) {
				projectService.removeClientFromProjects(clientId, username);
				clientRepository.delete(clientToDelete);
			}
		} catch (Exception e) {
			throw new ClientServiceException(exceptionUnwrapper.getExceptionMessage(e));
		}
	}
	
	private ClientEntity createClientEntityFromTO(ClientTO clientTO, UserEntity user) {
		Assert.notNull(clientTO, NULL_CLIENT_ERROR);
		
		ClientEntity client = new ClientEntity();
		client.setName(clientTO.getName());
		client.setSurname(clientTO.getSurname());
		client.setTelephoneNumbers(clientTO.getTelephoneNumbers());
		client.setUserEntity(user);
		client.setEmailAddress(clientTO.getEmailAddress());
		
		return client;
	}
}
