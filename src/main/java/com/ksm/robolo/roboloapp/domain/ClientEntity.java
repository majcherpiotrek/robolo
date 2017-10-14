package com.ksm.robolo.roboloapp.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "Clients")
public class ClientEntity {

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

	private String emailAddress;
	
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
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ClientEntity)) return false;

		ClientEntity that = (ClientEntity) o;

		if (!getName().equals(that.getName())) return false;
		if (!getSurname().equals(that.getSurname())) return false;
		if (!getTelephoneNumbers().equals(that.getTelephoneNumbers())) return false;
		return getEmailAddress() != null ? getEmailAddress().equals(that.getEmailAddress()) : that.getEmailAddress() == null;
	}

	@Override
	public int hashCode() {
		int result = getName().hashCode();
		result = 31 * result + getSurname().hashCode();
		result = 31 * result + getTelephoneNumbers().hashCode();
		result = 31 * result + (getEmailAddress() != null ? getEmailAddress().hashCode() : 0);
		return result;
	}
}
