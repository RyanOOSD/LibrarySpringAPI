package org.example.libraryspringapi.controller;

import org.example.libraryspringapi.entity.Author;
import org.example.libraryspringapi.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping()
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping("/new")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        authorService.addAuthor(author);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        Author updated = authorService.updateAuthor(id, author);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{author_id}/add-to-book/{book_id}")
    public void addAuthorToBook(@PathVariable Long author_id, @PathVariable Long book_id) {
        authorService.addAuthorToBook(author_id, book_id);
    }

    @PostMapping("/{author_id}/remove-from-book/{book_id}")
    public void removeAuthorFromBook(@PathVariable Long author_id, @PathVariable Long book_id) {
        authorService.removeAuthorFromBook(author_id, book_id);
    }
}
