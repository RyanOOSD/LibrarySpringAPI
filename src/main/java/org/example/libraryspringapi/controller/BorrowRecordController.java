package org.example.libraryspringapi.controller;

import org.example.libraryspringapi.entity.BorrowRecord;
import org.example.libraryspringapi.entity.auth.CustomUserDetails;
import org.example.libraryspringapi.service.BorrowRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrow-record")
public class BorrowRecordController {

    private BorrowRecordService borrowRecordService;

    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    @GetMapping()
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordService.getAllBorrowRecords();
    }

    @GetMapping("/{id}")
    public BorrowRecord getBorrowRecordById(@PathVariable Long id) {
        return borrowRecordService.getBorrowRecordById(id);
    }

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

    @PreAuthorize("authentication.principal.libraryMemberId == #member_id or hasRole('ROLE_LIBRARIAN')")
    @PostMapping("/{member_id}/return/{book_id}")
    public ResponseEntity<BorrowRecord> setBorrowRecordReturnDate(@PathVariable Long member_id, @PathVariable Long book_id) {
        borrowRecordService.setBorrowRecordReturnDate(member_id, book_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowRecord> updateBorrowRecord(@PathVariable Long id, @RequestBody BorrowRecord borrowRecord) {
        BorrowRecord updated = borrowRecordService.updateBorrowRecord(id, borrowRecord);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowRecord(@PathVariable Long id) {
        borrowRecordService.deleteBorrowRecord(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{record_id}/add-to-member/{member_id}")
    public ResponseEntity<BorrowRecord> addBorrowRecordToLibraryMember(@PathVariable Long record_id, @PathVariable Long member_id) {
        Optional<BorrowRecord> updated = borrowRecordService.addBorrowRecordToLibraryMember(record_id, member_id);
        if (updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{record_id}/remove-from-member/{member_id}")
    public ResponseEntity<BorrowRecord> removeBorrowRecordFromLibraryMember(@PathVariable Long record_id, @PathVariable Long member_id) {
        Optional<BorrowRecord> updated = borrowRecordService.removeBorrowRecordFromLibraryMember(record_id, member_id);
        if (updated.isPresent()) {
            return new ResponseEntity<>(updated.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

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
