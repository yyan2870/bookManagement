package com.example.bookManagement.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
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
@RequestMapping("/book")
@CrossOrigin 
public class BookController {
	
	@GetMapping(params = { "page", "size" })
	public List<String> findPaginated(@RequestParam("page") int page, 
	  @RequestParam("size") int size, UriComponentsBuilder uriBuilder,
	  HttpServletResponse response) {
		// First page with 5 items
//        Pageable paging = PageRequest.of(
//            0, 5, Sort.by("user").ascending());
//        Page<UserEntity> page = data.findAll(paging);
// 
//        // Retrieve the items
//        return page.getContent();
		
		List<String> list = new ArrayList<String>();
		
		list.add("test");
		list.add("foo");
		return list;
	}
}