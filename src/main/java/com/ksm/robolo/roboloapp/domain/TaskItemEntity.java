package com.ksm.robolo.roboloapp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Tasks_items")
public class TaskItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Integer quantity;

	@NotNull
	private String description;

	private Double price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TaskItemEntity)) return false;

		TaskItemEntity that = (TaskItemEntity) o;

		if (getQuantity() != null ? !getQuantity().equals(that.getQuantity()) : that.getQuantity() != null)
			return false;
		if (!getDescription().equals(that.getDescription())) return false;
		return getPrice() != null ? getPrice().equals(that.getPrice()) : that.getPrice() == null;
	}

	@Override
	public int hashCode() {
		int result = getQuantity() != null ? getQuantity().hashCode() : 0;
		result = 31 * result + getDescription().hashCode();
		result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
		return result;
	}
}
