package com.ksm.robolo.roboloapp.tos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ksm.robolo.roboloapp.domain.AddressEntity;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class ProjectTO {

    private Long id;

    private String projectName;
    
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    private AddressEntity address;

    private ClientTO clientTO;

    private List<WorkerTO> workerTOS;

    private List<TaskTO> taskTOS;
    
    @JsonFormat(pattern="yyyy-MM-dd")
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

    public List<WorkerTO> getWorkerTOS() {
        return workerTOS;
    }

    public void setWorkerTOS(List<WorkerTO> workerTOS) {
        this.workerTOS = workerTOS;
    }

    public List<TaskTO> getTaskTOS() {
        return taskTOS;
    }

    public void setTaskTOS(List<TaskTO> taskTOS) {
        this.taskTOS = taskTOS;
    }

    public Date getApproximateEndDate() {
        return approximateEndDate;
    }

    public void setApproximateEndDate(Date approximateEndDate) {
        this.approximateEndDate = approximateEndDate;
    }
}
