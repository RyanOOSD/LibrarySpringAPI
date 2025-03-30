package org.example.libraryspringapi.controller;

import org.example.libraryspringapi.entity.LibraryMember;
import org.example.libraryspringapi.entity.MembershipCard;
import org.example.libraryspringapi.service.LibraryMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
// Sets base URL as http://localhost:8080/api/library-member
@RequestMapping("/api/library-member")
public class LibraryMemberController {

    private final LibraryMemberService libraryMemberService;

    // Access service methods through constructor
    public LibraryMemberController(LibraryMemberService libraryMemberService) {
        this.libraryMemberService = libraryMemberService;
    }

    // Endpoint to get a list of all library members (/api/library-member)
    @GetMapping()
    public List<LibraryMember> getAllLibraryMembers() {
        return libraryMemberService.getAllLibraryMembers();
    }

    // Endpoint to get library member by ID
    @GetMapping("/{id}")
    public LibraryMember getLibraryMemberById(@PathVariable Long id) {
        return libraryMemberService.getLibraryMemberById(id);
    }

    // Endpoint to create a new library member with or without a new membership card
    @PostMapping("/new")
    public ResponseEntity<LibraryMember> createLibraryMember(@RequestBody LibraryMember libraryMember) {
        libraryMemberService.addLibraryMember(libraryMember);
        return new ResponseEntity<>(libraryMember, HttpStatus.CREATED);
    }

    // Endpoint to update a library member
    @PutMapping("/{id}")
    public ResponseEntity<LibraryMember> updateLibraryMember(@PathVariable Long id, @RequestBody LibraryMember libraryMember) {
        LibraryMember updated = libraryMemberService.updateLibraryMember(id, libraryMember);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Endpoint to delete library member by ID - also deletes associated membership card, if applicable
    // Returns custom message in JSON if the library member could not be deleted
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteLibraryMember(@PathVariable Long id) {
        String msg = libraryMemberService.deleteLibraryMember(id);
        Map<String, String> response = new HashMap<>();
        if (msg.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            response.put("message", msg);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Endpoint to get a list of all membership cards
    @GetMapping("/card")
    public List<MembershipCard> getAllMembershipCards() {
        return libraryMemberService.getAllMembershipCards();
    }

    // Endpoint to get a membership card by ID
    @GetMapping("/card/{id}")
    public MembershipCard getMembershipCardById(@PathVariable Long id) {
        return libraryMemberService.getMembershipCardById(id);
    }

    // Endpoint to create a new membership card independently of a library member
    @PostMapping("/new-card")
    public ResponseEntity<MembershipCard> createMembershipCard(@RequestBody MembershipCard membershipCard) {
        libraryMemberService.addMembershipCard(membershipCard);
        return new ResponseEntity<>(membershipCard, HttpStatus.CREATED);
    }

    // Endpoint to create a new membership card and associate it with an existing library member
    @PostMapping("/new-card/{member_id}")
    public ResponseEntity<LibraryMember> addNewMembershipCardToLibraryMember(@PathVariable Long member_id, @RequestBody MembershipCard membershipCard) {
        libraryMemberService.addNewMembershipCardToMember(member_id, membershipCard);
        LibraryMember member = libraryMemberService.getLibraryMemberById(member_id);
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    // Endpoint to update a membership card by ID
    @PutMapping("/update-card/{card_id}")
    public ResponseEntity<MembershipCard> updateMembershipCard(@PathVariable Long card_id, @RequestBody MembershipCard membershipCard) {
        MembershipCard updated = libraryMemberService.updateMembershipCard(card_id, membershipCard);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Endpoint to delete a membership card by ID - preserves associated library member, if applicable
    @DeleteMapping("/delete-card/{card_id}")
    public ResponseEntity<Void> deleteMembershipCard(@PathVariable Long card_id) {
        libraryMemberService.deleteMembershipCard(card_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}