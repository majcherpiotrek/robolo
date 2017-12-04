package com.ksm.robolo.roboloapp.services;

import java.util.List;

import com.ksm.robolo.roboloapp.services.exceptions.ClientServiceException;
import com.ksm.robolo.roboloapp.tos.ClientTO;

public interface ClientService {

	void addClient(ClientTO clientTO, String username) throws ClientServiceException;
	
	List<ClientTO> getAllClients(String username);
	
	void deleteClient(Long clientId, String username) throws ClientServiceException;
}
