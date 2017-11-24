package com.ksm.robolo.roboloapp.tos;

import com.ksm.robolo.roboloapp.domain.AddressEntity;

import java.util.Date;

public class ProjectStubTO {

    private Long id;

    private String projectName;

    private Date startDate;

    private AddressEntity address;

    private ClientTO clientTO;

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

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public ClientTO getClientTO() {
        return clientTO;
    }

    public void setClientTO(ClientTO clientTO) {
        this.clientTO = clientTO;
    }
}
