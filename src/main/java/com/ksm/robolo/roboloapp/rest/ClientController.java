package com.ksm.robolo.roboloapp.rest;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ksm.robolo.roboloapp.services.ClientService;
import com.ksm.robolo.roboloapp.services.exceptions.ClientServiceException;
import com.ksm.robolo.roboloapp.tos.ClientTO;

@RestController
@RequestMapping("/clients")
public class ClientController {

	private ClientService clientService;
	
	@Autowired
	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<List<ClientTO>> addClient(@RequestBody ClientTO clientTO, Principal principal) {
		List<ClientTO> updatedList = new LinkedList<>();
		try {
			clientService.addClient(clientTO, principal.getName());
			updatedList = clientService.getAllClients(principal.getName());
		} catch (ClientServiceException e) {
			e.printStackTrace();
			return new ResponseEntity<List<ClientTO>>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<List<ClientTO>>(updatedList, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{clientId}")
	public ResponseEntity<List<ClientTO>> deleteClient(@PathVariable Long clientId, Principal principal) {
		List<ClientTO> updatedList = new LinkedList<>();
		try {
			clientService.deleteClient(clientId, principal.getName());
			updatedList = clientService.getAllClients(principal.getName());
		} catch (ClientServiceException e) {
			e.printStackTrace();
			return new ResponseEntity<List<ClientTO>>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<List<ClientTO>>(updatedList, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<ClientTO>> getAllClients(Principal principal) {
		return new ResponseEntity<List<ClientTO>>(clientService.getAllClients(principal.getName()), HttpStatus.OK);
	}
}
