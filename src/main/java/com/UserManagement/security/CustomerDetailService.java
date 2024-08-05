package com.UserManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.UserManagement.entity.Student;
import com.UserManagement.exceptions.ResourceNotFoundEception;
import com.UserManagement.repo.StudentRepository;

@Service
public class CustomerDetailService implements UserDetailsService{

	@Autowired
	private StudentRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//loading user from database as username(email)
		Student user = this.userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFoundEception("User", "username"+username, 0));
		
		return user;
	}

}
