package com.example.bookManagement.service;
import com.example.bookManagement.model.Book;
// Importing required classes
import java.util.List;

// Interface
public interface BookService {

	// Save operation
	Book saveBook(Book book);
	
	// Read operation
	List<Book> fetchBookList();

	// Update operation
	Book updateBook(Book book, Long bookId);

	// Delete operation
	void deleteBookById(Long bookId);
}