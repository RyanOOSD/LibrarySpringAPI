package org.example.libraryspringapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.util.Date;

@Entity
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    // Formats incoming/outgoing JSON
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "The borrow date cannot exceed today's date!")
    @NotNull(message = "The borrow date cannot be null!")
    @Column(nullable = false)
    private Date borrowDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = true)
    private Date returnDate;

    // Adds library_member_id as a FK-constrained column from library member
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "library_member_id", referencedColumnName = "id")
    @JsonBackReference(value = "member-books")
    private LibraryMember libraryMember;

    // Adds book_id as a FK-constrained column from book
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @JsonBackReference(value = "book-records")
    private Book book;

    public BorrowRecord() {
    }

    public BorrowRecord(Date borrowDate, Date returnDate) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public void setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
                "id=" + id +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
