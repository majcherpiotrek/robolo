package com.ksm.robolo.roboloapp.dto;

import java.util.Date;
import java.util.Set;

public class Project {

    private Long id;

    private String projectName;

    private Date startDate;

    private AddressDTO addressDTO;

    private Client client;

    private Set<Worker> workers;

    private Set<Task> tasks;

    private Date approximateEndDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<Worker> workers) {
        this.workers = workers;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Date getApproximateEndDate() {
        return approximateEndDate;
    }

    public void setApproximateEndDate(Date approximateEndDate) {
        this.approximateEndDate = approximateEndDate;
    }
}
