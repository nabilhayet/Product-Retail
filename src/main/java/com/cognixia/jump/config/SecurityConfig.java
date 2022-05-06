package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognixia.jump.filters.JwtRequestFilter;
import com.cognixia.jump.service.MyUserDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(myUserDetailsService);
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers( HttpMethod.GET, "/v3/api-docs/").permitAll()
		
		.antMatchers( HttpMethod.POST, "/api/users").permitAll()
		.antMatchers( HttpMethod.PUT, "/api/users").hasRole("USER")
		.antMatchers( HttpMethod.GET, "/api/users").hasRole("ADMIN")
		
		.antMatchers( HttpMethod.POST, "/api/orders").hasRole("USER")
		.antMatchers( HttpMethod.GET, "/api/orders").hasRole("USER")
		
		.antMatchers( HttpMethod.GET, "/api/products").permitAll()
		.antMatchers( HttpMethod.POST, "/api/products").hasRole("ADMIN")
		.antMatchers( HttpMethod.DELETE, "/api/products/{id}").hasRole("ADMIN")
		
		.antMatchers("/api/authenticate").permitAll() // permit anyone to create a token as long as they are valid users
		.anyRequest().authenticated() // any request to any of out api needs to be authenticated token or users info
		.and().sessionManagement()
		.sessionCreationPolicy( SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
}
