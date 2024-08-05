package com.UserManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UserManagement.payload.StudentDTO;
import com.UserManagement.payload.SubjectDTO;
import com.UserManagement.services.SubjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/create")
    public ResponseEntity<SubjectDTO> createSubject(@Valid @RequestBody SubjectDTO subdto) {
		
		SubjectDTO createdsubDto = subjectService.createSubject(subdto);
		return new ResponseEntity<>(createdsubDto,HttpStatus.CREATED);
    }

	@GetMapping("/all")
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        return ResponseEntity.ok(this.subjectService.getAllSubjects());
    }

}
