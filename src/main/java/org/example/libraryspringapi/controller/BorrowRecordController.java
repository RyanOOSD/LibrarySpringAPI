package org.example.libraryspringapi.controller;

import org.example.libraryspringapi.entity.BorrowRecord;
import org.example.libraryspringapi.entity.auth.CustomUserDetails;
import org.example.libraryspringapi.service.BorrowRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
// Sets base URL as http://localhost:8080/api/borrow-record
@RequestMapping("/api/borrow-record")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    // Access service methods through constructor
    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    // Endpoint to get a list of all borrow records (/api/borrow-record)
    @GetMapping()
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordService.getAllBorrowRecords();
    }

    // Endpoint to get a borrow record by ID
    @GetMapping("/{id}")
    public BorrowRecord getBorrowRecordById(@PathVariable Long id) {
        return borrowRecordService.getBorrowRecordById(id);
    }

    // Endpoint to get a list of all borrow records for a library member
    // @PreAuthorize annotation used to restrict logged-in library members to only get their own borrow records
    // Also allows librarians to get the borrow records for any member
    @PreAuthorize("authentication.principal.libraryMemberId == #member_id or hasRole('ROLE_LIBRARIAN')")
    @GetMapping("/history/{member_id}")
    public ResponseEntity<List<BorrowRecord>> getBorrowRecordByMemberId(@PathVariable Long member_id) {
        System.out.println("Principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        System.out.println("LibraryMemberId: " + ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLibraryMemberId());

        Optional<List<BorrowRecord>> borrowRecords = borrowRecordService.getBorrowRecordsByLibraryMember(member_id);
        if (borrowRecords.isPresent()) {
            return new ResponseEntity<>(borrowRecords.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to check out a book
    // @PreAuthorize annotation used to restrict logged-in library members to only check-out books for themselves
    // Also allows librarians to check out books for any member
    @PreAuthorize("authentication.principal.libraryMemberId == #member_id or hasRole('ROLE_LIBRARIAN')")
    @PostMapping("/{member_id}/check-out/{book_id}")
    public ResponseEntity<BorrowRecord> createBorrowRecord(@PathVariable Long member_id, @PathVariable Long book_id, @RequestBody BorrowRecord borrowRecord) {
        Optional<BorrowRecord> newBorrowRecord = borrowRecordService.addBorrowRecord(member_id, book_id, borrowRecord);
        if (newBorrowRecord.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newBorrowRecord.get());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to return a book after it has been checked-out
    // @PreAuthorize annotation used to restrict logged-in members to only return books for themselves
    // Also allows librarians to return books for any member
    @PreAuthorize("authentication.principal.libraryMemberId == #member_id or hasRole('ROLE_LIBRARIAN')")
    @PostMapping("/{member_id}/return/{book_id}")
    public ResponseEntity<BorrowRecord> setBorrowRecordReturnDate(@PathVariable Long member_id, @PathVariable Long book_id) {
        borrowRecordService.setBorrowRecordReturnDate(member_id, book_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Endpoint to update a borrow record by ID
    @PutMapping("/{id}")
    public ResponseEntity<BorrowRecord> updateBorrowRecord(@PathVariable Long id, @RequestBody BorrowRecord borrowRecord) {
        BorrowRecord updated = borrowRecordService.updateBorrowRecord(id, borrowRecord);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Endpoint to delete a borrow record by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowRecord(@PathVariable Long id) {
        borrowRecordService.deleteBorrowRecord(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Endpoint to add a borrow record to a library member
    @PostMapping("/{record_id}/add-to-member/{member_id}")
    public ResponseEntity<BorrowRecord> addBorrowRecordToLibraryMember(@PathVariable Long record_id, @PathVariable Long member_id) {
        Optional<BorrowRecord> updated = borrowRecordService.addBorrowRecordToLibraryMember(record_id, member_id);
        if (updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to remove a borrow record from a library member
    @PostMapping("/{record_id}/remove-from-member/{member_id}")
    public ResponseEntity<BorrowRecord> removeBorrowRecordFromLibraryMember(@PathVariable Long record_id, @PathVariable Long member_id) {
        Optional<BorrowRecord> updated = borrowRecordService.removeBorrowRecordFromLibraryMember(record_id, member_id);
        if (updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // Commented out optional endpoints to add/remove a book from a borrow record
//    @PostMapping("/{record_id}/add-book/{book_id}")
//    public ResponseEntity<BorrowRecord> addBookToBorrowRecord(@PathVariable Long record_id, @PathVariable Long book_id) {
//        Optional<BorrowRecord> updated = borrowRecordService.addBookToBorrowRecord(record_id, book_id);
//        if (updated.isPresent()) {
//            return new ResponseEntity<>(updated.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

//    @PostMapping("/{record_id}/remove-book/")
//    public ResponseEntity<BorrowRecord> removeBookFromBorrowRecord(@PathVariable Long record_id) {
//        Optional<BorrowRecord> updated = borrowRecordService.removeBookFromBorrowRecord(record_id);
//        if (updated.isPresent()) {
//            return new ResponseEntity<>(updated.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
}
