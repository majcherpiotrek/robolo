package com.ksm.robolo.roboloapp.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Projects")
public class ProjectEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String projectName;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@OneToOne
	private AddressEntity address;
	
	@ManyToOne
	private ClientEntity client;
	
	@ManyToMany(targetEntity = WorkerEntity.class)
	private Set<WorkerEntity> workers;

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

	public ClientEntity getClient() {
		return client;
	}

	public void setClient(ClientEntity client) {
		this.client = client;
	}

	public Set<WorkerEntity> getWorkers() {
		return workers;
	}

	public void setWorkers(Set<WorkerEntity> workers) {
		this.workers = workers;
	}
}
