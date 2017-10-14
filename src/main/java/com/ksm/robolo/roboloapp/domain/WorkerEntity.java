package com.ksm.robolo.roboloapp.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Workers")
public class WorkerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String surname;

	@NotNull
	@ElementCollection
	private List<String> telephoneNumbers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<String> getTelephoneNumbers() {
		return telephoneNumbers;
	}

	public void setTelephoneNumbers(List<String> telephoneNumbers) {
		this.telephoneNumbers = telephoneNumbers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof WorkerEntity)) return false;

		WorkerEntity that = (WorkerEntity) o;

		if (!getName().equals(that.getName())) return false;
		if (!getSurname().equals(that.getSurname())) return false;
		return getTelephoneNumbers().equals(that.getTelephoneNumbers());
	}

	@Override
	public int hashCode() {
		int result = getName().hashCode();
		result = 31 * result + getSurname().hashCode();
		result = 31 * result + getTelephoneNumbers().hashCode();
		return result;
	}
}
