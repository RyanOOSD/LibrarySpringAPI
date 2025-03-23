package org.example.libraryspringapi.controller;

import org.example.libraryspringapi.entity.BorrowRecord;
import org.example.libraryspringapi.service.BorrowRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/new")
    public ResponseEntity<BorrowRecord> createBorrowRecord(@RequestBody BorrowRecord borrowRecord) {
        borrowRecordService.addBorrowRecord(borrowRecord);
        return new ResponseEntity<>(borrowRecord, HttpStatus.CREATED);
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
    public void addBorrowRecordToLibraryMember(@PathVariable Long record_id, @PathVariable Long member_id) {
        borrowRecordService.addBorrowRecordToLibraryMember(record_id, member_id);
    }

    @PostMapping("/{record_id}/remove-from-member/{member_id}")
    public void removeBorrowRecordFromLibraryMember(@PathVariable Long record_id, @PathVariable Long member_id) {
        borrowRecordService.removeBorrowRecordFromLibraryMember(record_id, member_id);
    }

    @PostMapping("/{record_id}/add-book/{book_id}")
    public void addBookToBorrowRecord(@PathVariable Long record_id, @PathVariable Long book_id) {
        borrowRecordService.addBookToBorrowRecord(record_id, book_id);
    }

    @PostMapping("/{record_id}/remove-book/{book_id}")
    public void removeBookFromBorrowRecord(@PathVariable Long record_id, @PathVariable Long book_id) {
        borrowRecordService.removeBookFromBorrowRecord(record_id, book_id);
    }
}
