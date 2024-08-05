package com.UserManagement.repo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UserManagement.entity.Student;
import com.UserManagement.payload.StudentDTO;

public interface StudentRepository extends JpaRepository<Student, Long>{
	
	Optional<Student> findByUsername(String username);
}
