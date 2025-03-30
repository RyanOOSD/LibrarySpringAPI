package org.example.libraryspringapi.controller;

import org.example.libraryspringapi.entity.Author;
import org.example.libraryspringapi.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
// Sets base URL as http://localhost:8080/api/author
@RequestMapping("/api/author")
public class AuthorController {

    private AuthorService authorService;

    // Access service methods through constructor
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Endpoint to get a list of all authors (/api/author)
    @GetMapping()
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    // Endpoint to get an author by ID
    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    // Endpoint to create a new author
    @PostMapping("/new")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        authorService.addAuthor(author);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    // Endpoint to update an author by ID
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        Author updated = authorService.updateAuthor(id, author);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Endpoint to delete an author by ID
    // Returns custom message in JSON if the author could not be deleted
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAuthor(@PathVariable Long id) {
        String msg = authorService.deleteAuthor(id);
        Map<String, String> response = new HashMap<>();
        if (msg.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            response.put("message", msg);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Endpoint to add an author to a book
    @PostMapping("/{author_id}/add-to-book/{book_id}")
    public ResponseEntity<Author> addAuthorToBook(@PathVariable Long author_id, @PathVariable Long book_id) {
        Optional<Author> updated = authorService.addAuthorToBook(author_id, book_id);
        if (updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to remove an author from a book
    @PostMapping("/{author_id}/remove-from-book/{book_id}")
    public ResponseEntity<Author> removeAuthorFromBook(@PathVariable Long author_id, @PathVariable Long book_id) {
        Optional<Author> updated = authorService.removeAuthorFromBook(author_id, book_id);
        if (updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
