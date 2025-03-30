package org.example.libraryspringapi.controller;

import org.example.libraryspringapi.entity.Book;
import org.example.libraryspringapi.service.AuthorService;
import org.example.libraryspringapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
// Sets base URL as http://localhost:8080/api/book
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    // Access service methods through constructor
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Endpoint to get a list of all books (/api/book)
    @GetMapping()
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Endpoint to get a list of books that are not currently borrowed
    @GetMapping("/available")
    public List<Book> getAvailableBooks() {
        return bookService.getAllAvailableBooks();
    }

    // Endpoint to get a book by ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // Endpoint to create a new book by author ID
    // Returns HTTP 400 if book could not be created
    @PostMapping("/new/{author_id}")
    public ResponseEntity<Book> createBook(@PathVariable Long author_id, @RequestBody Book book) {
        bookService.addBook(author_id, book);
        if (book.getId() != null) {
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to update a book by ID
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updated = bookService.updateBook(id, book);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Endpoint to delete a book by ID
    // Returns a custom message in JSON if the book could not be deleted
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteBook(@PathVariable Long id) {
        String msg = bookService.deleteBook(id);
        Map<String, String> response = new HashMap<>();
        if (msg.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            response.put("message", msg);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
