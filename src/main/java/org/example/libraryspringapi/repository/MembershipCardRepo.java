package org.example.libraryspringapi.repository;

import org.example.libraryspringapi.entity.MembershipCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository for membership card
@Repository
public interface MembershipCardRepo extends JpaRepository<MembershipCard, Long> {

}