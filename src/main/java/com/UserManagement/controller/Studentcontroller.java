package com.UserManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UserManagement.payload.ApiResponse;
import com.UserManagement.payload.StudentDTO;
import com.UserManagement.services.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class Studentcontroller {

	@Autowired
	private StudentService studentService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/students")
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO userdto) {
		
		StudentDTO createdUserdto = studentService.createStudent(userdto);
		return new ResponseEntity<>(createdUserdto,HttpStatus.CREATED);
    }

	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/students/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable Long id) {
    	studentService.DeleteStudent(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Student Deleted Successfully", true),HttpStatus.OK);
    }

	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/viewStudents")
	public ResponseEntity<List<StudentDTO>> getAllStudents(){
		return ResponseEntity.ok(this.studentService.getAllStudents());
	}
	
}
