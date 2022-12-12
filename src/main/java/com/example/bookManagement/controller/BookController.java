package com.example.bookManagement.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.example.bookManagement.model.Book;
import com.example.bookManagement.model.User;
import com.example.bookManagement.model.UserRole;
import com.example.bookManagement.repository.BookRepository;
import com.example.bookManagement.service.BookService;

import lombok.var;

import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/book")
@CrossOrigin 
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookRepository bookRepo;
	
	@GetMapping(params = { "page", "size" })
	public List<Book> findPaginated(@RequestParam("page") int page, 
	  @RequestParam("size") int size, UriComponentsBuilder uriBuilder,
	  HttpServletResponse response) {
	  
		Pageable pagebleRequest = PageRequest.of(page, size, Sort.by("id").ascending());

	    Page<Book> pageBooks =  bookRepo.findAll(pagebleRequest);
	    

	    return pageBooks.getContent();
	
	}
	
	@GetMapping("")
	public List<Book> fetchBookList() {
		return bookService.fetchBookList();
	}
	
}