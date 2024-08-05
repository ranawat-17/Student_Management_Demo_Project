package com.UserManagement.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JwtAuthRequest {
	
	@NotBlank(message = "username should not be blank or empty")
	private String username;
	
	@NotBlank(message = "password should not be blank or empty")
	private String password;
}
