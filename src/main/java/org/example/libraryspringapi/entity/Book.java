package org.example.libraryspringapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank(message = "The book's title is required!")
    @Column(nullable = false, unique = true)
    private String title;

    @NotBlank(message = "The book's ISBN is required!")
    @Column(nullable = false, unique = true)
    private String isbn;

    @Positive(message = "The book's publication year cannot be negative!")
    @Max(value = 2025, message = "The book's publication year cannot exceed 2025!")
    @NotNull(message = "The book's publication year is required!")
    @Column(nullable = false)
    private Integer publicationYear;

    // Initialize authors attribute to an empty HashSet
    @ManyToMany(mappedBy = "books", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    private Set<Author> authors = new HashSet<>();

    // Initialize borrowRecords attribute to an empty ArrayList
    @OneToMany(mappedBy = "book", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JsonManagedReference(value = "book-records")
    private List<BorrowRecord> borrowRecords = new ArrayList<>();

    public Book() {
    }

    public Book(Integer publicationYear, String isbn, String title) {
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public List<BorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }

    public void setBorrowRecords(List<BorrowRecord> borrowRecords) {
        this.borrowRecords = borrowRecords;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                ", authors=" + authors +
                '}';
    }
}
