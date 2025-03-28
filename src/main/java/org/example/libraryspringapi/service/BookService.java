package org.example.libraryspringapi.service;

import org.example.libraryspringapi.entity.Author;
import org.example.libraryspringapi.entity.Book;
import org.example.libraryspringapi.entity.BorrowRecord;
import org.example.libraryspringapi.repository.AuthorRepo;
import org.example.libraryspringapi.repository.BookRepo;
import org.example.libraryspringapi.repository.BorrowRecordRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private BookRepo bookRepo;
    private AuthorRepo authorRepo;
    private BorrowRecordRepo borrowRecordRepo;

    // Access repos with constructor DI
    public BookService(BookRepo bookRepo, AuthorRepo authorRepo, BorrowRecordRepo borrowRecordRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.borrowRecordRepo = borrowRecordRepo;
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public List<Book> getAllAvailableBooks() {
        List<Book> allBooks = bookRepo.findAll();
        List<Book> availableBooks = new ArrayList<>();

        for (Book book : allBooks) {
            if (!borrowRecordRepo.existsByBookAndReturnDateIsNull(book)) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    // Get book by ID
    public Book getBookById(Long id) {
        return bookRepo.findById(id).orElse(null);
    }

    // Create a book
    public void addBook(Long authorId, Book newBook) {
        Author author = authorRepo.findById(authorId).orElse(null);
        if (author != null) {
            newBook.getAuthors().add(author);
            author.getBooks().add(newBook);
            bookRepo.save(newBook);
            authorRepo.save(author);
        }
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
    public String deleteBook(Long id) {
        Book book = bookRepo.findById(id).orElse(null);
        if (book != null) {
            for (BorrowRecord borrowRecord : book.getBorrowRecords()) {
                if (borrowRecord.getReturnDate() == null) {
                    return "This book cannot be deleted, it is currently borrowed!";
                }
            }
            for (Author author : book.getAuthors()) {
                author.getBooks().remove(book);
                authorRepo.save(author);
            }
            book.setAuthors(null);
            bookRepo.deleteById(id);
            return "";
        } else {
            return "The book you are trying to delete does not exist!";
        }
    }
}
