package org.example.libraryspringapi.repository;

import org.example.libraryspringapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository for book
@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
}
