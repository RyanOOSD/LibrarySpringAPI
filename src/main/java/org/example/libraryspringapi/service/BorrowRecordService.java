package org.example.libraryspringapi.service;

import org.example.libraryspringapi.entity.Book;
import org.example.libraryspringapi.entity.BorrowRecord;
import org.example.libraryspringapi.entity.LibraryMember;
import org.example.libraryspringapi.repository.BookRepo;
import org.example.libraryspringapi.repository.BorrowRecordRepo;
import org.example.libraryspringapi.repository.LibraryMemberRepo;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Optional<List<BorrowRecord>> getBorrowRecordsByLibraryMember(Long libraryMemberId) {
        LibraryMember libraryMember = libraryMemberRepo.findById(libraryMemberId).orElse(null);
        if (libraryMember != null) {
            List<BorrowRecord> borrowRecords = borrowRecordRepo.findBorrowRecordByLibraryMember(libraryMember);
            return Optional.of(borrowRecords);
        }
        return Optional.empty();
    }

    // Get borrow record by ID
    public BorrowRecord getBorrowRecordById(Long id) {
        return borrowRecordRepo.findById(id).orElse(null);
    }

    // Create a borrow record - must be associated with a library member and book
    // A book can only be checked out by one member at a time
    public Optional<BorrowRecord> addBorrowRecord(Long authorId, Long bookId, BorrowRecord newBorrowRecord) {
        LibraryMember libraryMember = libraryMemberRepo.findById(authorId).orElse(null);
        Book book = bookRepo.findById(bookId).orElse(null);
        // Check if the book has already been checked out
        if (borrowRecordRepo.existsByBookAndReturnDateIsNull(book)) {
            return Optional.empty();
        }
        if (libraryMember != null && book != null) {
            newBorrowRecord.setLibraryMember(libraryMember);
            newBorrowRecord.setBook(book);
        }
        BorrowRecord saved = borrowRecordRepo.save(newBorrowRecord);
        return Optional.of(saved);
    }

    // Returning a book - sets returnDate to current date
    public void setBorrowRecordReturnDate(Long memberId, Long bookId) {
        LibraryMember libraryMember = libraryMemberRepo.findById(memberId).orElse(null);
        Book book = bookRepo.findById(bookId).orElse(null);
        if (libraryMember != null && book != null) {
            BorrowRecord borrowRecord = borrowRecordRepo.findTopByBookAndLibraryMemberOrderByBorrowDateDesc(book, libraryMember);
            if (borrowRecord.getReturnDate() == null) {
                Date returnDate = new Date();
                borrowRecord.setReturnDate(returnDate);
            }
            borrowRecordRepo.save(borrowRecord);
        }
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
    public Optional<BorrowRecord> addBorrowRecordToLibraryMember(Long borrowRecordId, Long libraryMemberId) {
        LibraryMember libraryMember = libraryMemberRepo.findById(libraryMemberId).orElse(null);
        BorrowRecord borrowRecord = borrowRecordRepo.findById(borrowRecordId).orElse(null);
        if (libraryMember != null && borrowRecord != null) {
            libraryMember.getBorrowedBooks().add(borrowRecord);
            libraryMemberRepo.save(libraryMember);
            return Optional.of(borrowRecord);
        }
        return Optional.empty();
    }

    // Remove a borrow record from a library member
    public Optional<BorrowRecord> removeBorrowRecordFromLibraryMember(Long borrowRecordId, Long libraryMemberId) {
        LibraryMember libraryMember = libraryMemberRepo.findById(libraryMemberId).orElse(null);
        BorrowRecord borrowRecord = borrowRecordRepo.findById(borrowRecordId).orElse(null);
        if (libraryMember != null && borrowRecord != null) {
            libraryMember.getBorrowedBooks().remove(borrowRecord);
            libraryMemberRepo.save(libraryMember);
            return Optional.of(borrowRecord);
        }
        return Optional.empty();
    }

    // Add a book to a borrow record
    public Optional<BorrowRecord> addBookToBorrowRecord(Long bookId, Long borrowRecordId) {
        Book book = bookRepo.findById(bookId).orElse(null);
        BorrowRecord borrowRecord = borrowRecordRepo.findById(borrowRecordId).orElse(null);
        if (book != null && borrowRecord != null) {
            borrowRecord.setBook(book);
            borrowRecordRepo.save(borrowRecord);
            return Optional.of(borrowRecord);
        }
        return Optional.empty();
    }

    // Remove a book from a borrow record
    public Optional<BorrowRecord> removeBookFromBorrowRecord(Long borrowRecordId) {
        BorrowRecord borrowRecord = borrowRecordRepo.findById(borrowRecordId).orElse(null);
        if (borrowRecord != null) {
            borrowRecord.setBook(null);
            borrowRecordRepo.save(borrowRecord);
            return Optional.of(borrowRecord);
        }
        return Optional.empty();
    }
}
