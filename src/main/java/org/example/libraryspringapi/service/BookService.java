package org.example.libraryspringapi.service;

import org.example.libraryspringapi.entity.Book;
import org.example.libraryspringapi.repository.AuthorRepo;
import org.example.libraryspringapi.repository.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private BookRepo bookRepo;

    // Access repos with constructor DI
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    // Get book by ID
    public Book getBookById(Long id) {
        return bookRepo.findById(id).orElse(null);
    }

    // Create a book
    public void addBook(Book newBook) {
        bookRepo.save(newBook);
    }

    // Update a book
    public Book updateBook(Long id, Book updatedBookDetails) {
        // Get existing book
        Book bookToUpdate = bookRepo.findById(id).orElse(null);
        if (bookToUpdate != null) {
            bookToUpdate.setTitle(updatedBookDetails.getTitle());
            bookToUpdate.setIsbn(updatedBookDetails.getIsbn());
            bookToUpdate.setPublicationYear(updatedBookDetails.getPublicationYear());
        }
        return bookRepo.save(bookToUpdate);
    }

    // Delete a book
    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }
}
