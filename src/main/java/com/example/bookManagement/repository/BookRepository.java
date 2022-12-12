package com.example.bookManagement.repository;
// Importing required classes
import com.example.bookManagement.model.Book;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Annotation
@Repository
// Interface
public interface BookRepository extends JpaRepository<Book, Long> {
	
	 // Page<Book> findAllPageable(Pageable pageable);
}