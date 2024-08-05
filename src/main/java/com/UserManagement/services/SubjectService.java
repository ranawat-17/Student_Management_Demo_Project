package com.UserManagement.services;

import java.util.List;

import com.UserManagement.payload.SubjectDTO;

public interface SubjectService {
	
	SubjectDTO createSubject(SubjectDTO subjectDTO);
	List<SubjectDTO> getAllSubjects();
}
