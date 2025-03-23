package org.example.libraryspringapi.service;

import org.example.libraryspringapi.entity.Book;
import org.example.libraryspringapi.entity.BorrowRecord;
import org.example.libraryspringapi.entity.LibraryMember;
import org.example.libraryspringapi.repository.BookRepo;
import org.example.libraryspringapi.repository.BorrowRecordRepo;
import org.example.libraryspringapi.repository.LibraryMemberRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowRecordService {

    private BorrowRecordRepo borrowRecordRepo;
    private LibraryMemberRepo libraryMemberRepo;
    private BookRepo bookRepo;

    public BorrowRecordService(BorrowRecordRepo borrowRecordRepo, LibraryMemberRepo libraryMemberRepo, BookRepo bookRepo) {
        this.borrowRecordRepo = borrowRecordRepo;
        this.libraryMemberRepo = libraryMemberRepo;
        this.bookRepo = bookRepo;
    }

    // Get all borrow records
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepo.findAll();
    }

    // Get borrow record by ID
    public BorrowRecord getBorrowRecordById(Long id) {
        return borrowRecordRepo.findById(id).orElse(null);
    }

    // Create a borrow record
    public void addBorrowRecord(BorrowRecord newBorrowRecord) {
        borrowRecordRepo.save(newBorrowRecord);
    }

    // Update a borrow record
    public BorrowRecord updateBorrowRecord(Long id, BorrowRecord updatedBorrowRecord) {
        BorrowRecord borrowRecordToUpdate = borrowRecordRepo.findById(id).orElse(null);
        if (borrowRecordToUpdate != null) {
            borrowRecordToUpdate.setBorrowDate(updatedBorrowRecord.getBorrowDate());
            borrowRecordToUpdate.setReturnDate(updatedBorrowRecord.getReturnDate());
        }
        return borrowRecordRepo.save(borrowRecordToUpdate);
    }

    // Delete a borrow record
    public void deleteBorrowRecord(Long id) {
        borrowRecordRepo.deleteById(id);
    }

    // Add a borrow record to a library member
    public void addBorrowRecordToLibraryMember(Long borrowRecordId, Long libraryMemberId) {
        LibraryMember libraryMember = libraryMemberRepo.findById(libraryMemberId).orElse(null);
        BorrowRecord borrowRecord = borrowRecordRepo.findById(borrowRecordId).orElse(null);
        if (libraryMember != null && borrowRecord != null) {
            libraryMember.getBorrowedBooks().add(borrowRecord);
        }
        libraryMemberRepo.save(libraryMember);
    }

    // Remove a borrow record from a library member
    public void removeBorrowRecordFromLibraryMember(Long borrowRecordId, Long libraryMemberId) {
        LibraryMember libraryMember = libraryMemberRepo.findById(libraryMemberId).orElse(null);
        BorrowRecord borrowRecord = borrowRecordRepo.findById(borrowRecordId).orElse(null);
        if (libraryMember != null && borrowRecord != null) {
            libraryMember.getBorrowedBooks().remove(borrowRecord);
        }
        libraryMemberRepo.save(libraryMember);
    }

    // Add a book to a borrow record
    public void addBookToBorrowRecord(Long bookId, Long borrowRecordId) {
        Book book = bookRepo.findById(bookId).orElse(null);
        BorrowRecord borrowRecord = borrowRecordRepo.findById(borrowRecordId).orElse(null);
        if (book != null && borrowRecord != null) {
            borrowRecord.setBook(book);
        }
        borrowRecordRepo.save(borrowRecord);
    }

    // Remove a book from a borrow record
    public void removeBookFromBorrowRecord(Long bookId, Long borrowRecordId) {
        BorrowRecord borrowRecord = borrowRecordRepo.findById(borrowRecordId).orElse(null);
        if (borrowRecord != null) {
            borrowRecord.setBook(null);
        }
        borrowRecordRepo.save(borrowRecord);
    }
}
