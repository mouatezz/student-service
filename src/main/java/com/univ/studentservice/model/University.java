package com.univ.studentservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "university")
public class University {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	private String location;

	// Default constructor
	public University() {}

	// All args constructor
	public University(Long id, String name, String location) {
		this.id = id;
		this.name = name;
		this.location = location;
	}

	// Getters and setters
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}


