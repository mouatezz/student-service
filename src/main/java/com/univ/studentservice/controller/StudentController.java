package com.univ.studentservice.controller;

import com.univ.studentservice.dto.StudentRequestDTO;
import com.univ.studentservice.dto.StudentResponseDTO;
import com.univ.studentservice.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin("*")
@Tag(name = "Student Controller", description = "REST API for Student management")
public class StudentController {

	@Autowired private StudentService service;

	@PostMapping
	@Operation(summary = "Create a new student", description = "Creates a new student with the provided details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Student created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "404", description = "University not found")
	})
	public ResponseEntity<StudentResponseDTO> add(@Valid @RequestBody StudentRequestDTO dto) {
		StudentResponseDTO created = service.add(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@GetMapping
	@Operation(summary = "Get all students", description = "Retrieves all students, optionally filtered by university")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
			@ApiResponse(responseCode = "404", description = "University not found (if universityId provided)")
	})
	public ResponseEntity<List<StudentResponseDTO>> all(
			@Parameter(description = "Filter by university ID") @RequestParam(required = false) Long universityId) {
		if (universityId != null) {
			return ResponseEntity.ok(service.getByUniversity(universityId));
		}
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get student by ID", description = "Retrieves a specific student by their ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student found"),
			@ApiResponse(responseCode = "404", description = "Student not found")
	})
	public ResponseEntity<StudentResponseDTO> one(
			@Parameter(description = "Student ID") @PathVariable Long id) {
		StudentResponseDTO student = service.getById(id);
		return ResponseEntity.ok(student);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update student", description = "Updates an existing student with the provided details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "404", description = "Student or University not found")
	})
	public ResponseEntity<StudentResponseDTO> update(
			@Parameter(description = "Student ID") @PathVariable Long id,
			@Valid @RequestBody StudentRequestDTO dto) {
		StudentResponseDTO updated = service.update(id, dto);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete student", description = "Deletes a student by their ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Student deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Student not found")
	})
	public ResponseEntity<Void> delete(
			@Parameter(description = "Student ID") @PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	@Operation(summary = "Search students", description = "Searches students by name, ID, or university name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Search completed successfully")
	})
	public ResponseEntity<List<StudentResponseDTO>> search(
			@Parameter(description = "Search query (name, ID, or university name)") @RequestParam(required = false) String query) {
		return ResponseEntity.ok(service.search(query));
	}
}
