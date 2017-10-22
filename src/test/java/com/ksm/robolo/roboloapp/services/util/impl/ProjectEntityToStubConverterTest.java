package com.ksm.robolo.roboloapp.services.util.impl;

import com.ksm.robolo.roboloapp.domain.AddressEntity;
import com.ksm.robolo.roboloapp.domain.ClientEntity;
import com.ksm.robolo.roboloapp.domain.ProjectEntity;
import com.ksm.robolo.roboloapp.domain.WorkerEntity;
import com.ksm.robolo.roboloapp.tos.ClientTO;
import com.ksm.robolo.roboloapp.tos.ProjectStubTO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ProjectEntityToStubConverterTest {
    @Test
    public void ProjectEntityToProjectToTest() throws Exception {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setApartmentNumber("1");
        addressEntity.setCity("Radom");
        addressEntity.setCountry("Uzbekistan");
        addressEntity.setId(7l);
        addressEntity.setPostCode("11-000");
        addressEntity.setStreet("random");
        addressEntity.setHouseNumber("10");


        final ClientEntity clientEntity = new ClientEntity();
        Long id = 777l;
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

        List<WorkerEntity> workerList = new ArrayList<>();
        WorkerEntity workerEntity = new WorkerEntity();
        workerEntity.setId(9l);
        workerEntity.setName("Jebacz");
        workerEntity.setSurname("Fizyczny");
        workerEntity.setTelephoneNumbers(telephoneNumbers);
        workerList.add(workerEntity);



        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(100l);
        projectEntity.setAddress(addressEntity);
        projectEntity.setClient(clientEntity);
        projectEntity.setProjectName("Test");
        projectEntity.setStartDate(new Date());
        projectEntity.setWorkers(workerList);


        final ProjectStubTO projectStubTO = new ProjectEntityToStubConverter().convertToTO(projectEntity);

        assertEquals(addressEntity,projectStubTO.getAddressTO());
        assertEquals(clientTO,projectStubTO.getClientTO());
        assertEquals(projectEntity.getProjectName(),projectStubTO.getProjectName());
        assertEquals(projectEntity.getStartDate(),projectStubTO.getStartDate());

    }


}