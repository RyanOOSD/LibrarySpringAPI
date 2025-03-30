package org.example.libraryspringapi.repository;

import org.example.libraryspringapi.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository for author
@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {
}
