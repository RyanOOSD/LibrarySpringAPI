package org.example.libraryspringapi.controller.auth;

import org.example.libraryspringapi.entity.auth.LibraryUser;
import org.example.libraryspringapi.service.auth.LibraryUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RegistrationController {

    private final LibraryUserService libraryUserService;

    // Access service methods through constructor
    public RegistrationController(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    // Endpoint to register a new librarian with "LIBRARIAN" role by library member ID
    @PostMapping("/register-librarian/{member_id}")
    public ResponseEntity<LibraryUser> registerLibrarian(@PathVariable Long member_id, @RequestBody LibraryUser libraryUser) {
        libraryUser.setRole("LIBRARIAN");
        libraryUser.setPassword(new BCryptPasswordEncoder().encode(libraryUser.getPassword()));
        Optional<LibraryUser> newLibrarian = libraryUserService.addLibraryUser(member_id, libraryUser);
        if (newLibrarian.isPresent()) {
            return new ResponseEntity<>(newLibrarian.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to register a new library user with "MEMBER" role by library member ID
    @PostMapping("/register-member/{member_id}")
    public ResponseEntity<LibraryUser> registerMember(@PathVariable Long member_id, @RequestBody LibraryUser libraryUser) {
        libraryUser.setRole("MEMBER");
        libraryUser.setPassword(new BCryptPasswordEncoder().encode(libraryUser.getPassword()));
        Optional<LibraryUser> newLibraryUser = libraryUserService.addLibraryUser(member_id, libraryUser);
        if (newLibraryUser.isPresent()) {
            return new ResponseEntity<>(newLibraryUser.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to update a user by ID
    @PutMapping("/user/{id}")
    public ResponseEntity<LibraryUser> updateUser(@PathVariable Long id, @RequestBody LibraryUser libraryUser) {
        LibraryUser updated = libraryUserService.updateLibraryUser(id, libraryUser);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Endpoint to delete a user by ID
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        libraryUserService.deleteLibraryUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
