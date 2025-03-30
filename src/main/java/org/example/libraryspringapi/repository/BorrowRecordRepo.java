package org.example.libraryspringapi.repository;

import org.example.libraryspringapi.entity.Book;
import org.example.libraryspringapi.entity.BorrowRecord;
import org.example.libraryspringapi.entity.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository for borrow records
@Repository
public interface BorrowRecordRepo extends JpaRepository<BorrowRecord, Long> {
    // Check if a book is already checked out
    boolean existsByBookAndReturnDateIsNull(Book book);

    // Get the most recent BorrowRecord based on the library member and the book
    BorrowRecord findTopByBookAndLibraryMemberOrderByBorrowDateDesc(Book book, LibraryMember libraryMember);

    // Get a list of borrow records for the specified library member
    List<BorrowRecord> findBorrowRecordByLibraryMember(LibraryMember libraryMember);
}
