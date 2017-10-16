package com.ksm.robolo.roboloapp.services.impl;

import com.ksm.robolo.roboloapp.domain.*;
import com.ksm.robolo.roboloapp.enums.TaskStatus;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TestObjectsFactory {

    public ProjectEntity createProjectEntity() {
        Long id = 1L;
        String projectName = "project name";
        Date startDate = new Date();
        String street = "street";
        String number = "number";
        String apartmentNumber = "apart number";
        String city = "city";
        String postCode = "post code";
        String country = "country";
        String name = "name";
        String surname = "surname";
        String emailAddress = "email@domain.com";
        String telephoneNumber = "123456789";

        // Create address entity
        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(id);
        addressEntity.setStreet(street);
        addressEntity.setHouseNumber(number);
        addressEntity.setApartmentNumber(apartmentNumber);
        addressEntity.setCity(city);
        addressEntity.setPostCode(postCode);
        addressEntity.setCountry(country);

        // create client entity
        final ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(id);
        clientEntity.setName(name);
        clientEntity.setSurname(surname);
        clientEntity.setEmailAddress(emailAddress);
        List<String> telephoneNumbers = new LinkedList<>();
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

        return projectEntity;
    }

    public TaskEntity createTaskEntityForProjectEntity(ProjectEntity projectEntity) {
        Long id = 1L;
        Date startDate = new Date();


        final TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(id);
        String description = "description";
        taskEntity.setDescription(description);
        taskEntity.setCreationDate(startDate);
        taskEntity.setStatus(TaskStatus.IN_PROGRESS);
        int estimatedTaskDuration = 1;
        taskEntity.setEstimatedTaskDuration(estimatedTaskDuration);
        taskEntity.setProject(projectEntity);
        taskEntity.setWorkers(projectEntity.getWorkers());

        List<TaskItemEntity> taskItemEntityList = new LinkedList<>();
        TaskItemEntity taskItemEntity = new TaskItemEntity();
        taskItemEntity.setId(id);
        taskItemEntity.setDescription(description);
        double price = 10.0;
        taskItemEntity.setPrice(price);
        int quantity = 10;
        taskItemEntity.setQuantity(quantity);
        taskItemEntityList.add(taskItemEntity);

        taskEntity.setTaskItems(taskItemEntityList);

        return taskEntity;
    }
}
