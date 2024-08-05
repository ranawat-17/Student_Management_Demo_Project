package com.UserManagement.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.UserManagement.entity.Role;
import com.UserManagement.entity.Student;
import com.UserManagement.entity.Subject;
import com.UserManagement.exceptions.ResourceNotFoundEception;
import com.UserManagement.payload.StudentDTO;
import com.UserManagement.payload.SubjectDTO;
import com.UserManagement.repo.RoleRepo;
import com.UserManagement.repo.SubjectRepository;
import com.UserManagement.repo.StudentRepository;
import com.UserManagement.services.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private SubjectRepository subjectRepository;

	@Override
	public StudentDTO createStudent(StudentDTO userDTO) {
		
		Student user = this.DtoToUser(userDTO);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		Role role = this.roleRepo.findById(502).get();
		user.getRoles().add(role);
		Student saveduser = studentRepository.save(user);
		return this.UserToDto(saveduser);
	}

	@Override
	public void DeleteStudent(Long userId) {
		Student user = this.studentRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundEception("User", "Id", userId));
		user.getRoles().clear();
		studentRepository.save(user);
		studentRepository.delete(user);
	}

	@Override
	public List<StudentDTO> getAllStudents() {
		
		List<Student> users = this.studentRepository.findAll();
		List<StudentDTO> userdtos = users.stream().map(user -> this.UserToDto(user)).collect(Collectors.toList());
	
		return userdtos;
	}

	@Override
	public StudentDTO findByUsername(String username) {
		Student user = studentRepository.findByUsername(username).get();
		return this.UserToDto(user);
	}
	@Override
	public StudentDTO registerNewStudent(StudentDTO userDto) {
		
		Student user = DtoToUser(userDto);
		
		//set password ecode
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		Role role = this.roleRepo.findById(502).get();
		//setrole
		user.getRoles().add(role);
		Student newUser = this.studentRepository.save(user);
		
		return this.UserToDto(newUser);
	}
	
	public Student DtoToUser(StudentDTO userDto) {
	
		Student user = new Student();
		user.setId(userDto.getId());
		user.setUsername(userDto.getUsername());
		user.setPassword(userDto.getPassword());
		user.setName(userDto.getName());
		user.setAddress(userDto.getAddress());
		
		Set<Subject> subjects = userDto.getSubjects().stream()
                .map(subjectDto -> subjectRepository.findById(subjectDto.getId())
                .orElseThrow(() -> new RuntimeException("Subject not found")))
                .collect(Collectors.toSet());
		
		
		user.setSubjects(subjects);
		return user;	
	}
	
	public StudentDTO UserToDto(Student user) {
		
		StudentDTO userDto = new StudentDTO();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setPassword(user.getPassword());
		userDto.setName(user.getName());
		userDto.setAddress(user.getAddress());
		userDto.setSubjects(convertToDTOSubjects(user.getSubjects()));
		userDto.setRoles(user.getRoles());
		return userDto;
	}
	
//	private Set<Subject> convertToSubjects(Set<SubjectDTO> subjectDTOs) {
//        Set<Subject> subjects = new HashSet<>();
//        for (SubjectDTO dto : subjectDTOs) {
//            Subject subject = new Subject();
//            subject.setId(dto.getId()); 
//            subject.setName(dto.getName()); 
//            subjects.add(subject);
//        }
//        return subjects;
//    }
	private Set<SubjectDTO> convertToDTOSubjects(Set<Subject> subject) {
        Set<SubjectDTO> subjectdtos = new HashSet<>();
        for (Subject sub : subject) {
            SubjectDTO subjectdto = new SubjectDTO();
            subjectdto.setId(sub.getId()); 
            subjectdto.setName(sub.getName()); 
            subjectdtos.add(subjectdto);
        }
        return subjectdtos;
    }	
}
