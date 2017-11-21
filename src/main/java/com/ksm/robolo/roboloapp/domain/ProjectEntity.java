package com.ksm.robolo.roboloapp.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Projects")
public class ProjectEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(targetEntity = UserEntity.class)
    @NotNull
	private UserEntity userEntity;

	@NotNull
	private String projectName;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@NotNull
	@OneToOne
	private AddressEntity address;

	@ManyToOne
	private ClientEntity client;
	
	@ManyToMany(targetEntity = WorkerEntity.class)
	private List<WorkerEntity> workers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
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

	public List<WorkerEntity> getWorkers() {
		return workers;
	}

	public void setWorkers(List<WorkerEntity> workers) {
		this.workers = workers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ProjectEntity)) return false;

		ProjectEntity that = (ProjectEntity) o;

        if(!getUserEntity().equals(that.getUserEntity())) return false;
		if (!getProjectName().equals(that.getProjectName())) return false;
		if (!getStartDate().equals(that.getStartDate())) return false;
		if (!getAddress().equals(that.getAddress())) return false;
		return getClient().equals(that.getClient());
	}

	@Override
	public int hashCode() {
		int result = getProjectName().hashCode();
        result = 31 * result + getUserEntity().hashCode();
		result = 31 * result + getStartDate().hashCode();
		result = 31 * result + getAddress().hashCode();
		result = 31 * result + getClient().hashCode();
		return result;
	}
}
