package com.UserManagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.UserManagement.security.CustomerDetailService;
import com.UserManagement.security.JwtAuthenticationEntryPoint;
import com.UserManagement.security.JwtAuthenticationFilter;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private CustomerDetailService customerDetailService;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
			.csrf()
			.disable()
			.authorizeHttpRequests() 
			.requestMatchers("/api/v1/auth/**").permitAll()
//			.requestMatchers(HttpMethod.POST,"/api/students").hasRole("ADMIN")
//			.requestMatchers(HttpMethod.DELETE,"/api/students/{id}").hasRole("ADMIN")
//			.requestMatchers(HttpMethod.GET,"/api/viewStudents").hasRole("ADMIN")
			.requestMatchers(HttpMethod.GET,"/subjects/all").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(this.customerDetailService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return authenticationProvider;
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
