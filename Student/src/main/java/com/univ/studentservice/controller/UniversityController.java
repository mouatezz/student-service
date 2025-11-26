package com.univ.studentservice.controller;

import com.univ.studentservice.model.University;
import com.univ.studentservice.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/universities")
@CrossOrigin("*")
public class UniversityController {

	@Autowired private UniversityRepository repo;

	@PostMapping
	public University add(@RequestBody University u) { return repo.save(u); }

	@GetMapping
	public List<University> all() { return repo.findAll(); }

	@GetMapping("/{id}")
	public University one(@PathVariable Long id) {
		return repo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
	}

	@PutMapping("/{id}")
	public University update(@PathVariable Long id, @RequestBody University u) {
		University existing = one(id);
		existing.setName(u.getName());
		existing.setLocation(u.getLocation());
		return repo.save(existing);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		repo.deleteById(id);
		return "Deleted";
	}
}


