package com.univ.studentservice.dto;

public class StudentResponseDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Long universityId;
	private String universityName;
	private String universityLocation;

	// Default constructor
	public StudentResponseDTO() {}

	// All args constructor
	public StudentResponseDTO(Long id, String firstName, String lastName, String email, 
							  Long universityId, String universityName, String universityLocation) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.universityId = universityId;
		this.universityName = universityName;
		this.universityLocation = universityLocation;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public String getUniversityLocation() {
		return universityLocation;
	}

	public void setUniversityLocation(String universityLocation) {
		this.universityLocation = universityLocation;
	}
}




