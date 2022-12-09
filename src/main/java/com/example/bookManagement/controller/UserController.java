package com.example.bookManagement.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.bookManagement.model.User;
import com.example.bookManagement.model.UserRole;
import com.example.bookManagement.service.UserServiceImpl;


@RestController
@RequestMapping("/user")
@CrossOrigin 

public class UserController {
	@Autowired
	private UserServiceImpl userService;
	
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
}