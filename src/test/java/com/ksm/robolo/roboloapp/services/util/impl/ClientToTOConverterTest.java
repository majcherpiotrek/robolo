package com.ksm.robolo.roboloapp.services.util.impl;

import com.ksm.robolo.roboloapp.domain.ClientEntity;
import com.ksm.robolo.roboloapp.tos.ClientTO;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


public class ClientToTOConverterTest {
    // TODO create tests


    @Test
    public void covertClientEntityToClientToTest() throws Exception {
        final Long id = 1L;
        final String projectName = "project name";
        final Date startDate = new Date();

        final ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(id);
        final String email = "email@com";
        clientEntity.setEmailAddress(email);
        final String name = "Janusz";
        clientEntity.setName(name);
        final String surname = "Tracz";
        clientEntity.setSurname(surname);
        final List<String> telephoneNumbers = Collections.singletonList("997998999");
        clientEntity.setTelephoneNumbers(telephoneNumbers);


        final ClientTO clientTO = new ClientToTOConverter().convertToTO(clientEntity);

        assertEquals(id, clientTO.getId());
        assertEquals(email, clientTO.getEmailAddress());
        assertEquals(name, clientTO.getName());
        assertEquals(surname, clientTO.getSurname());
        assertEquals(telephoneNumbers, clientTO.getTelephoneNumbers());
    }
}