package com.project.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//the category entity, found as a document in the database
//encompasses all relevant data of a category (just the name)

@Document
public class Category {

	@Id
	private String id;

	private String name;

	private Status categoryStatus;

	public Category() {
		super();
	}

	public Category(String id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.categoryStatus = Status.ACTIVE;
	}

	public Category(String id, String name, Status categoryStatus) {
		super();
		this.id = id;
		this.name = name;
		this.categoryStatus = categoryStatus;
	}

	public Category(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Status getCategoryStatus() {
		return categoryStatus;
	}

	public void setCategoryStatus(Status categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
