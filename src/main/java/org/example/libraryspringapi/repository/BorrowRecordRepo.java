package org.example.libraryspringapi.repository;

import org.example.libraryspringapi.entity.Book;
import org.example.libraryspringapi.entity.BorrowRecord;
import org.example.libraryspringapi.entity.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BorrowRecordRepo extends JpaRepository<BorrowRecord, Long> {
    // Check if a book is already checked out
    boolean existsByBookAndReturnDateIsNull(Book book);

    // Get the most recent BorrowRecord based on the library member and the book
    BorrowRecord findTopByBookAndLibraryMemberOrderByBorrowDateDesc(Book book, LibraryMember libraryMember);
}
