package org.example.libraryspringapi.repository;

import org.example.libraryspringapi.entity.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryMemberRepo extends JpaRepository<LibraryMember, Long> {
}
