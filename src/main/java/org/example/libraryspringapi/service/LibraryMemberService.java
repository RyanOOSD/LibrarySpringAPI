package org.example.libraryspringapi.service;

import org.example.libraryspringapi.entity.LibraryMember;
import org.example.libraryspringapi.entity.MembershipCard;
import org.example.libraryspringapi.repository.LibraryMemberRepo;
import org.example.libraryspringapi.repository.MembershipCardRepo;
import org.springframework.stereotype.Service;

import java.util.List;

// Includes methods for MembershipCard
@Service
public class LibraryMemberService {

    private final MembershipCardRepo membershipCardRepo;
    private LibraryMemberRepo libraryMemberRepo;

    public LibraryMemberService(LibraryMemberRepo libraryMemberRepo, MembershipCardRepo membershipCardRepo) {
        this.libraryMemberRepo = libraryMemberRepo;
        this.membershipCardRepo = membershipCardRepo;
    }

    // Get all library members
    public List<LibraryMember> getAllLibraryMembers() {
        return libraryMemberRepo.findAll();
    }

    // Get library member by ID
    public LibraryMember getLibraryMemberById(Long id) {
        return libraryMemberRepo.findById(id).orElse(null);
    }

    // Create a library member
    public void addLibraryMember(LibraryMember newLibraryMember) {
        libraryMemberRepo.save(newLibraryMember);
    }

    // Update a library member
    public LibraryMember updateLibraryMember(LibraryMember updatedLibraryMember) {
        LibraryMember libraryMemberToUpdate = libraryMemberRepo.findById(updatedLibraryMember.getId()).orElse(null);
        if (libraryMemberToUpdate != null) {
            libraryMemberToUpdate.setName(updatedLibraryMember.getName());
            libraryMemberToUpdate.setEmail(updatedLibraryMember.getEmail());
            libraryMemberToUpdate.setMembershipDate(updatedLibraryMember.getMembershipDate());
            if (updatedLibraryMember.getMembershipCard() != null) {
                MembershipCard membershipCard = updatedLibraryMember.getMembershipCard();
                membershipCard.setId(libraryMemberToUpdate.getMembershipCard().getId());
                membershipCard.setLibraryMember(libraryMemberToUpdate);
                libraryMemberToUpdate.setMembershipCard(membershipCard);
            }
        }
        return libraryMemberRepo.save(libraryMemberToUpdate);
    }

    // Delete a library member
    public void deleteLibraryMember(Long id) {
        libraryMemberRepo.deleteById(id);
    }

    // Delete just a membership card
    public void deleteMembershipCard(Long id) {
        MembershipCard membershipCard = membershipCardRepo.findById(id).orElse(null);
        if (membershipCard != null) {
            LibraryMember libraryMember = membershipCard.getLibraryMember();
            libraryMember.setMembershipCard(null);
            libraryMemberRepo.save(libraryMember);
        }
        membershipCardRepo.deleteById(id);
    }
}
