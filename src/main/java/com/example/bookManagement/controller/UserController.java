package com.example.bookManagement.controller;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.bookManagement.model.User;
import com.example.bookManagement.model.UserRole;
import com.example.bookManagement.service.UserServiceImpl;

import lombok.var;

import org.springframework.http.ResponseCookie;
//import com.example.bookManagement.security.config.TokenProvider;
//import com.example.bookManagement.model.AuthToken;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/user")
@CrossOrigin 

public class UserController {
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
//	@Autowired
//    private TokenProvider jwtTokenUtil;


	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody RegisterUserRequestBody user) {
		
		System.out.print(user);
		
		User newUser = new User();
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setEmail(user.getEmail());
		newUser.setUserRole(UserRole.valueOf(user.getRole()));
		newUser.setPassword(user.getPassword());
		
		
		User registeredUser = userService.registerUser(newUser);
		
		if(registeredUser == null) {
			return ResponseEntity.notFound().build();
		} else {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			          .path("/{id}")
			          .buildAndExpand(registeredUser.getId())
			          .toUri();

	        return ResponseEntity.created(uri)
	          .body(registeredUser);
		}
		
	}
	
	
	 @PostMapping("/signin")
	  public ResponseEntity<?> authenticateUser( @RequestBody LoginRequestBody loginRequest) {
		
		 System.out.print(loginRequest.getEmail() + "," + loginRequest.getPassword());
		
		try {
			  Authentication authentication = authenticationManager
				        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
				    
				    var user = (User) authentication.getPrincipal();		 
				    
				    System.out.print("authentication" + ":" + user);
				    SecurityContextHolder.getContext().setAuthentication(authentication);
			        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
		} catch (BadCredentialsException ex) {
			 
			 System.out.print("exception: " + ex);

		      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	  
	  }
	 
	 //@GetMapping("/signout")
	
	
}