package org.example.libraryspringapi.repository;

import org.example.libraryspringapi.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRecordRepo extends JpaRepository<BorrowRecord, Long> {
}
