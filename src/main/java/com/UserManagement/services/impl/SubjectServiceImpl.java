package com.UserManagement.services.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.UserManagement.entity.Student;
import com.UserManagement.entity.Subject;
import com.UserManagement.payload.StudentDTO;
import com.UserManagement.payload.SubjectDTO;
import com.UserManagement.repo.SubjectRepository;
import com.UserManagement.services.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;
	
	@Override
	public SubjectDTO createSubject(SubjectDTO subjectDTO) {
		
		Subject subject = this.DtoToUser(subjectDTO);
		Subject savedsub = subjectRepository.save(subject);
		return this.UserToDTO(savedsub);
	}
	
	@Override
	public List<SubjectDTO> getAllSubjects() {
		List<Subject> subjects = this.subjectRepository.findAll();
		return subjects.stream().map(sub -> this.convertToSubjectDTO(sub)).collect(Collectors.toList());
	}
	
	private SubjectDTO convertToSubjectDTO(Subject subject) {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subject.getId());
        subjectDTO.setName(subject.getName());
        return subjectDTO;
    }

	public Subject DtoToUser(SubjectDTO userDto) {
		
		Subject user = new Subject();
		user.setName(userDto.getName());
		return user;	
	}
	public SubjectDTO UserToDTO(Subject user) {
		
		SubjectDTO userdto = new SubjectDTO();
		userdto.setName(user.getName());
		return userdto;	
	}

}
