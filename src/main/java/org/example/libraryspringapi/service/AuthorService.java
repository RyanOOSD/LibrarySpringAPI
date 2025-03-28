package org.example.libraryspringapi.service;

import org.example.libraryspringapi.entity.Author;
import org.example.libraryspringapi.entity.Book;
import org.example.libraryspringapi.repository.AuthorRepo;
import org.example.libraryspringapi.repository.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private AuthorRepo authorRepo;
    private BookRepo bookRepo;

    // Access repos with constructor DI
    public AuthorService(AuthorRepo authorRepo, BookRepo bookRepo) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
    }

    // Get all authors
    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    // Get author by ID
    public Author getAuthorById(long id) {
        return authorRepo.findById(id).orElse(null);
    }

    // Create an author
    public void addAuthor(Author newAuthor) {
        authorRepo.save(newAuthor);
    }

    // Update an author
    public Author updateAuthor(Long id, Author newAuthorDetails) {
        // Get existing author object
        Author authorToUpdate = authorRepo.findById(id).orElse(null);
        if (authorToUpdate != null) {
            authorToUpdate.setName(newAuthorDetails.getName());
            authorToUpdate.setBiography(newAuthorDetails.getBiography());
        }
        return authorRepo.save(authorToUpdate);
    }

    // Delete an author
    public String deleteAuthor(long id) {
        Author authorToDelete = authorRepo.findById(id).orElse(null);
        if (authorToDelete == null) {
            return "Author does not exist!";
        } else if (!authorToDelete.getBooks().isEmpty()) {
            return "Author cannot be deleted because it is associated with a book!";
        }
        authorRepo.deleteById(id);
        return "";
    }

    // Add an author to a book
    public Optional<Author> addAuthorToBook(Long authorId, Long bookId) {
        // Get existing author and book
        Author author = authorRepo.findById(authorId).orElse(null);
        Book book = bookRepo.findById(bookId).orElse(null);
        if (author != null && book != null) {
            author.getBooks().add(book);
            book.getAuthors().add(author);
            authorRepo.save(author);
            bookRepo.save(book);
            return Optional.of(author);
        }
        return Optional.empty();
    }

    // Remove author from a book
    public Optional<Author> removeAuthorFromBook(Long authorId, Long bookId) {
        // Get existing author and book
        Author author = authorRepo.findById(authorId).orElse(null);
        Book book = bookRepo.findById(bookId).orElse(null);
        if (author != null && book != null) {
            author.getBooks().remove(book);
            book.getAuthors().remove(author);
            authorRepo.save(author);
            bookRepo.save(book);
            return Optional.of(author);
        }
        return Optional.empty();
    }
}
