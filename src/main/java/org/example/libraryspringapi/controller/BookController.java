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
@RequestMapping("/api/book")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/available")
    public List<Book> getAvailableBooks() {
        return bookService.getAllAvailableBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/new/{author_id}")
    public ResponseEntity<Book> createBook(@PathVariable Long author_id, @RequestBody Book book) {
        bookService.addBook(author_id, book);
        if (book.getId() != null) {
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updated = bookService.updateBook(id, book);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

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
