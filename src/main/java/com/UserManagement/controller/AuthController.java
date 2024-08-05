package com.UserManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UserManagement.exceptions.ApiException;
import com.UserManagement.payload.JwtAuthRequest;
import com.UserManagement.payload.JwtAuthResponse;
import com.UserManagement.payload.StudentDTO;
import com.UserManagement.security.JwtTokenHelper;
import com.UserManagement.services.StudentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name = "User Management")
@RequestMapping("api/v1/auth/")
public class AuthController {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private StudentService studentService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@Valid
			@RequestBody JwtAuthRequest request) throws Exception{
		
		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			this.authenticationManager.authenticate(authenticationToken);	
		}catch (BadCredentialsException e) {
			System.out.println("Invalid Details!!");
			throw new ApiException("Invalid username or password");
		}
	}
	
	//register user
	@PostMapping("/register")
	public ResponseEntity<StudentDTO> registerUser(@Valid @RequestBody StudentDTO userDto){
		
		StudentDTO registeredUser = this.studentService.registerNewStudent(userDto);
		
		return new ResponseEntity<StudentDTO>(registeredUser,HttpStatus.CREATED);
	} 
	
	
}
