package com.UserManagement.services;

import java.util.List;

import com.UserManagement.payload.StudentDTO;

public interface StudentService {

	 StudentDTO registerNewStudent(StudentDTO userDto);
	 StudentDTO createStudent(StudentDTO userDTO);
	 void DeleteStudent(Long userId);
	 List<StudentDTO> getAllStudents();
	 StudentDTO findByUsername(String username);
}
