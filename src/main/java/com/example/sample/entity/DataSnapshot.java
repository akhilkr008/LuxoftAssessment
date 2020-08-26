package com.example.sample.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class DataSnapshot {
	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	private String name;
	private String description;
	private Calendar timestamp;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Version
	public Calendar getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Calendar tImestamp) {
		this.timestamp = tImestamp;
	}

}
