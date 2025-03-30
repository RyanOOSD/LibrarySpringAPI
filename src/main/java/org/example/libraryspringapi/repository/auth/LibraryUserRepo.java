package org.example.libraryspringapi.repository.auth;

import org.example.libraryspringapi.entity.auth.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repository for library user
@Repository
public interface LibraryUserRepo extends JpaRepository<LibraryUser, Long> {
    // Find the user in the database by username
    Optional<LibraryUser> findByUsername(String username);
}
