package com.UserManagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UserManagement.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

}
