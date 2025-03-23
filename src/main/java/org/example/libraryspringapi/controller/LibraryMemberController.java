package org.example.libraryspringapi.controller;

import org.example.libraryspringapi.entity.LibraryMember;
import org.example.libraryspringapi.service.LibraryMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Void> deleteLibraryMember(@PathVariable Long id) {
        libraryMemberService.deleteLibraryMember(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/delete-card/{id}")
    public ResponseEntity<Void> deleteMembershipCard(@PathVariable Long id) {
        libraryMemberService.deleteMembershipCard(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
