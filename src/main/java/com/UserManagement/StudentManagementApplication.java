package com.UserManagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.UserManagement.entity.Role;
import com.UserManagement.entity.Subject;
import com.UserManagement.repo.RoleRepo;
import com.UserManagement.repo.SubjectRepository;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@SecurityScheme(name = "User Management", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class StudentManagementApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private SubjectRepository subjectRepository;

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("Secure123"));
		
		try {
			//set roles
			Role role = new Role();
			role.setId(501);
			role.setName("ROLE_ADMIN");
			
			Role role1 = new Role();
			role1.setId(502);
			role1.setName("ROLE_NORMAL");
			
			List<Role> roles = List.of(role,role1);
			
			List<Role> result = this.roleRepo.saveAll(roles);
			
			result.forEach(r -> {
				System.out.println(r.getName());
				
			//-----------------------------------------------
				
			Subject subject = new Subject();
			subject.setId(1);
			subject.setName("Mathematics");
			
			Subject subject1 = new Subject();
			subject1.setId(2);
			subject1.setName("Science");
			
			List<Subject> subjects = List.of(subject,subject1);
			
			this.subjectRepository.saveAll(subjects);
			
			});
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
