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
@RequestMapping("/api/library-member")
public class LibraryMemberController {

    private LibraryMemberService libraryMemberService;

    public LibraryMemberController(LibraryMemberService libraryMemberService) {
        this.libraryMemberService = libraryMemberService;
    }

    @GetMapping()
    public List<LibraryMember> getAllLibraryMembers() {
        return libraryMemberService.getAllLibraryMembers();
    }

    @GetMapping("/{id}")
    public LibraryMember getLibraryMemberById(@PathVariable Long id) {
        return libraryMemberService.getLibraryMemberById(id);
    }

    @PostMapping("/new")
    public ResponseEntity<LibraryMember> createLibraryMember(@RequestBody LibraryMember libraryMember) {
        libraryMemberService.addLibraryMember(libraryMember);
        return new ResponseEntity<>(libraryMember, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibraryMember> updateLibraryMember(@PathVariable Long id, @RequestBody LibraryMember libraryMember) {
        LibraryMember updated = libraryMemberService.updateLibraryMember(id, libraryMember);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

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

    @GetMapping("/card")
    public List<MembershipCard> getAllMembershipCards() {
        return libraryMemberService.getAllMembershipCards();
    }

    @GetMapping("/card/{id}")
    public MembershipCard getMembershipCardById(@PathVariable Long id) {
        return libraryMemberService.getMembershipCardById(id);
    }

    @PostMapping("/new-card")
    public ResponseEntity<MembershipCard> createMembershipCard(@RequestBody MembershipCard membershipCard) {
        libraryMemberService.addMembershipCard(membershipCard);
        return new ResponseEntity<>(membershipCard, HttpStatus.CREATED);
    }

    @PostMapping("/new-card/{member_id}")
    public ResponseEntity<LibraryMember> addNewMembershipCardToLibraryMember(@PathVariable Long member_id, @RequestBody MembershipCard membershipCard) {
        libraryMemberService.addNewMembershipCardToMember(member_id, membershipCard);
        LibraryMember member = libraryMemberService.getLibraryMemberById(member_id);
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    @PutMapping("/update-card/{card_id}")
    public ResponseEntity<MembershipCard> updateMembershipCard(@PathVariable Long card_id, @RequestBody MembershipCard membershipCard) {
        MembershipCard updated = libraryMemberService.updateMembershipCard(card_id, membershipCard);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/delete-card/{card_id}")
    public ResponseEntity<Void> deleteMembershipCard(@PathVariable Long card_id) {
        libraryMemberService.deleteMembershipCard(card_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}