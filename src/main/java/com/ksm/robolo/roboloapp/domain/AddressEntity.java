package com.ksm.robolo.roboloapp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Projects_addresses")
public class AddressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String street;

	@NotNull
	private String houseNumber;

	@NotNull
	private String apartmentNumber;

	@NotNull
	private String city;

	@NotNull
	private String postCode;

	@NotNull
	private String country;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(String apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AddressEntity)) return false;

		AddressEntity that = (AddressEntity) o;

		if (!getStreet().equals(that.getStreet())) return false;
		if (!getHouseNumber().equals(that.getHouseNumber())) return false;
		if (!getApartmentNumber().equals(that.getApartmentNumber())) return false;
		if (!getCity().equals(that.getCity())) return false;
		if (!getPostCode().equals(that.getPostCode())) return false;
		return getCountry().equals(that.getCountry());
	}

	@Override
	public int hashCode() {
		int result = getStreet().hashCode();
		result = 31 * result + getHouseNumber().hashCode();
		result = 31 * result + getApartmentNumber().hashCode();
		result = 31 * result + getCity().hashCode();
		result = 31 * result + getPostCode().hashCode();
		result = 31 * result + getCountry().hashCode();
		return result;
	}
}
