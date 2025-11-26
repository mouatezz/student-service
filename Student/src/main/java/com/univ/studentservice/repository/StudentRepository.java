package com.univ.studentservice.repository;

import com.univ.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
	List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String first, String last);
	List<Student> findByUniversityId(Long universityId);
	List<Student> findByEmailContainingIgnoreCase(String email);
	java.util.Optional<Student> findByEmailIgnoreCase(String email);

	@Query("SELECT DISTINCT s FROM Student s WHERE " +
			"(:query IS NULL OR " +
			"(CAST(s.id AS string) = :query) OR " +
			"LOWER(s.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
			"LOWER(s.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
			"LOWER(s.university.name) LIKE LOWER(CONCAT('%', :query, '%')))")
	List<Student> searchByQuery(@Param("query") String query);
}


