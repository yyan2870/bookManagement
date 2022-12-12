// Importing package module to this code
package com.example.bookManagement.service;
import com.example.bookManagement.model.Book;
import com.example.bookManagement.repository.BookRepository;
// Importing required classes
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Annotation
@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepo;
	
	// Save operation
	@Override
	public Book saveBook(Book book) {
		return bookRepo.save(book);
	}
	
	// Read operation
	@Override public List<Book> fetchBookList() {
		return (List<Book>) bookRepo.findAll();
	}
	
	// Update operation
	@Override
	public Book updateBook(Book book, Long bookId) {
		Book foundBook = bookRepo.findById(bookId).get();
		return bookRepo.save(foundBook);
	}
	
	// Delete operation
	@Override
	public void deleteBookById(Long bookId) {
		bookRepo.deleteById(bookId);
	}
}