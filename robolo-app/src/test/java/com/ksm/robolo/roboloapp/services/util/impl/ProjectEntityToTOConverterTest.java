package com.ksm.robolo.roboloapp.services.util.impl;

import com.ksm.robolo.roboloapp.domain.*;
import com.ksm.robolo.roboloapp.tos.ProjectTO;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class ProjectEntityToTOConverterTest {

    private ProjectEntityToTOConverter projectEntityToTOConverter;

    @Test
    public void convertProjectEntityToProjectToTest() throws Exception {
        final Long id = 1L;
        final String projectName = "project name";
        final Date startDate = new Date();

        // Create address entity
        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(id);
        String street = "street";
        addressEntity.setStreet(street);
        String number = "number";
        addressEntity.setHouseNumber(number);
        String apartmentNumber = "apart number";
        addressEntity.setApartmentNumber(apartmentNumber);
        String city = "city";
        addressEntity.setCity(city);
        String postCode = "post code";
        addressEntity.setPostCode(postCode);
        String country = "country";
        addressEntity.setCountry(country);

        // create client entity
        final ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(id);
        String name = "name";
        clientEntity.setName(name);
        String surname = "surname";
        clientEntity.setSurname(surname);
        String emailAddress = "email@domain.com";
        clientEntity.setEmailAddress(emailAddress);
        List<String> telephoneNumbers = new LinkedList<>();
        String telephoneNumber = "123456789";
        telephoneNumbers.add(telephoneNumber);
        clientEntity.setTelephoneNumbers(telephoneNumbers);

        final WorkerEntity workerEntity = new WorkerEntity();
        workerEntity.setId(id);
        workerEntity.setName(name);
        workerEntity.setSurname(surname);
        workerEntity.setTelephoneNumbers(telephoneNumbers);

        final List<WorkerEntity> workers = new LinkedList<>();
        workers.add(workerEntity);

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(id);
        projectEntity.setProjectName(projectName);
        projectEntity.setStartDate(startDate);
        projectEntity.setAddress(addressEntity);
        projectEntity.setClient(clientEntity);
        projectEntity.setWorkers(workers);

        projectEntityToTOConverter = new ProjectEntityToTOConverter();
        ProjectTO projectTO = projectEntityToTOConverter.convertToTO(projectEntity);

        assertEquals(id, projectTO.getId());
        assertEquals(projectName, projectTO.getProjectName());
        assertEquals(startDate, projectTO.getStartDate());
        assertEquals(addressEntity.getStreet(), projectTO.getAddressTO().getStreet());
        assertEquals(clientEntity.getName(), projectTO.getClientTO().getName());
        assertEquals(workers.get(0).getName(), projectTO.getWorkerTOS().get(0).getName());
        assertTrue(projectTO.getTaskTOS().isEmpty());
        assertNull(projectTO.getApproximateEndDate());
    }
}