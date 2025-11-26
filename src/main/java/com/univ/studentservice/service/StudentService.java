package com.univ.studentservice.service;

import com.univ.studentservice.dto.StudentRequestDTO;
import com.univ.studentservice.dto.StudentResponseDTO;
import com.univ.studentservice.model.Student;
import com.univ.studentservice.model.University;
import com.univ.studentservice.repository.StudentRepository;
import com.univ.studentservice.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

	@Autowired private StudentRepository studentRepo;
	@Autowired private UniversityRepository univRepo;

	public StudentResponseDTO add(StudentRequestDTO dto) {
		validateUniv(dto.getUniversityId());
		University university = univRepo.findById(dto.getUniversityId())
				.orElseThrow(() -> new RuntimeException("University not found: " + dto.getUniversityId()));

		Student student = new Student();
		student.setFirstName(dto.getFirstName());
		student.setLastName(dto.getLastName());
		student.setEmail(dto.getEmail());
		student.setUniversity(university);

		Student saved = studentRepo.save(student);
		return toResponseDTO(saved);
	}

	public StudentResponseDTO update(Long id, StudentRequestDTO dto) {
		Student existing = findStudentById(id);
		validateUniv(dto.getUniversityId());
		University university = univRepo.findById(dto.getUniversityId())
				.orElseThrow(() -> new RuntimeException("University not found: " + dto.getUniversityId()));

		// Check if email is being changed and if it already exists for another student
		String existingEmail = existing.getEmail() != null ? existing.getEmail().trim() : "";
		String newEmail = dto.getEmail() != null ? dto.getEmail().trim() : "";
		
		if (!existingEmail.equalsIgnoreCase(newEmail)) {
			studentRepo.findByEmailIgnoreCase(newEmail)
					.filter(s -> !s.getId().equals(id)) // Exclude the current student
					.ifPresent(s -> {
						throw new RuntimeException("Email already exists: " + dto.getEmail());
					});
		}

		existing.setFirstName(dto.getFirstName());
		existing.setLastName(dto.getLastName());
		existing.setEmail(dto.getEmail());
		existing.setUniversity(university);

		Student updated = studentRepo.save(existing);
		return toResponseDTO(updated);
	}

	public void delete(Long id) {
		if (!studentRepo.existsById(id)) {
			throw new RuntimeException("Student not found with id: " + id);
		}
		studentRepo.deleteById(id);
	}

	public List<StudentResponseDTO> getAll() {
		return studentRepo.findAll().stream()
				.map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	public StudentResponseDTO getById(Long id) {
		Student student = studentRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
		return toResponseDTO(student);
	}

	public List<StudentResponseDTO> search(String query) {
		if (query == null || query.trim().isEmpty()) {
			return getAll();
		}
		
		String trimmedQuery = query.trim();
		
		// Search by ID (if numeric), name, or university name
		// The searchByQuery method handles all cases in one query
		return studentRepo.searchByQuery(trimmedQuery).stream()
				.map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	public List<StudentResponseDTO> getByUniversity(Long univId) {
		validateUniv(univId);
		return studentRepo.findByUniversityId(univId).stream()
				.map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	private Student findStudentById(Long id) {
		return studentRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
	}

	private void validateUniv(Long id) {
		if (!univRepo.existsById(id)) {
			throw new RuntimeException("University not found: " + id);
		}
	}

	private StudentResponseDTO toResponseDTO(Student student) {
		StudentResponseDTO dto = new StudentResponseDTO();
		dto.setId(student.getId());
		dto.setFirstName(student.getFirstName());
		dto.setLastName(student.getLastName());
		dto.setEmail(student.getEmail());
		dto.setUniversityId(student.getUniversity().getId());
		dto.setUniversityName(student.getUniversity().getName());
		dto.setUniversityLocation(student.getUniversity().getLocation());
		return dto;
	}
}
