package com.UserManagement.payload;

import java.util.HashSet;
import java.util.Set;

import com.UserManagement.entity.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentDTO {
	private Long id;
	
	@NotBlank(message = "username should not be blank or empty")
    private String username;
	
	@Size(min = 3, max = 10)
	@NotBlank(message = "password should not be blank or empty")
    private String password;
	
	@NotBlank(message = "name should not be blank or empty")
    private String name;
	
	@NotBlank(message = "address should not be blank or empty")
    private String address;
	
	private Set<SubjectDTO> subjects;
    
    private Set<Role> roles = new HashSet<>();
}
