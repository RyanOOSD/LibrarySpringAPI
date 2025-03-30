package org.example.libraryspringapi.service;

import org.example.libraryspringapi.entity.BorrowRecord;
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
    private final LibraryMemberRepo libraryMemberRepo;

    // Access repos through constructor DI
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

    // Update a library member and their membership card
    public LibraryMember updateLibraryMember(Long id, LibraryMember updatedLibraryMember) {
        LibraryMember libraryMemberToUpdate = libraryMemberRepo.findById(id).orElse(null);
        if (libraryMemberToUpdate != null) {
            libraryMemberToUpdate.setName(updatedLibraryMember.getName());
            libraryMemberToUpdate.setEmail(updatedLibraryMember.getEmail());
            libraryMemberToUpdate.setMembershipDate(updatedLibraryMember.getMembershipDate());
            // If the incoming data includes membership card information, update it
            if (updatedLibraryMember.getMembershipCard() != null) {
                MembershipCard membershipCard = updatedLibraryMember.getMembershipCard();
                membershipCard.setId(libraryMemberToUpdate.getMembershipCard().getId());
                membershipCard.setLibraryMember(libraryMemberToUpdate);
                libraryMemberToUpdate.setMembershipCard(membershipCard);
            }
        }
        return libraryMemberRepo.save(libraryMemberToUpdate);
    }

    // Delete a library member and their membership card as long as all books are returned
    public String deleteLibraryMember(Long id) {
        LibraryMember libraryMemberToDelete = libraryMemberRepo.findById(id).orElse(null);
        // Check if a library member has ever borrowed any books
        if (libraryMemberToDelete != null && !libraryMemberToDelete.getBorrowedBooks().isEmpty()) {
            // Iterate through their borrowed books and check that they have all been returned
            for (BorrowRecord books : libraryMemberToDelete.getBorrowedBooks()) {
                if (books.getReturnDate() == null) {
                    // Return custom error message
                    return "Library member cannot be deleted, they still have books checked out!";
                }
            }
        }
        libraryMemberRepo.deleteById(id);
        return "";
    }

    // Get all membership cards
    public List<MembershipCard> getAllMembershipCards() {
        return membershipCardRepo.findAll();
    }

    // Get membership card by ID
    public MembershipCard getMembershipCardById(Long id) {
        return membershipCardRepo.findById(id).orElse(null);
    }

    // Add just a new membership card
    public void addMembershipCard(MembershipCard membershipCard) {
        membershipCardRepo.save(membershipCard);
    }

    // Add a new membership card to a member
    public void addNewMembershipCardToMember(Long member_id, MembershipCard membershipCard) {
        // Save the membership card before retrieving the library member and adding the card to it
        membershipCardRepo.save(membershipCard);
        LibraryMember member = libraryMemberRepo.findById(member_id).orElse(null);
        if (member != null) {
            member.setMembershipCard(membershipCard);
        }
        libraryMemberRepo.save(member);
    }

    // Update just the membership card
    public MembershipCard updateMembershipCard(Long id, MembershipCard membershipCard) {
        MembershipCard membershipCardToUpdate = membershipCardRepo.findById(id).orElse(null);
        if (membershipCardToUpdate != null) {
            membershipCardToUpdate.setCardNumber((membershipCard.getCardNumber()));
            membershipCardToUpdate.setIssueDate((membershipCard.getIssueDate()));
            membershipCardToUpdate.setExpiryDate((membershipCard.getExpiryDate()));
        }
        return membershipCardRepo.save(membershipCardToUpdate);
    }

    // Delete a membership card without deleting the library member
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
