package org.example.libraryspringapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
public class LibraryMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank(message = "The member's name is required!")
    @Column(nullable = false)
    private String name;

    @Email
    @NotBlank(message = "The member's email is required!")
    @Column(nullable = false, unique = true)
    private String email;

    // If we were also handling input via query parameters or form data
    // You could use the annotation below to format the date
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // Formats incoming/outgoing JSON
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "The borrow date must be in the format yyyy-MM-dd!")
    @NotNull(message = "The member's registration date is required!")
    @Column(nullable = false)
    private Date membershipDate;

    // Since membershipCard is FK in this table, it is the "owner" of the relationship
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "membership_card_id", referencedColumnName = "id")
    @JsonManagedReference
    private MembershipCard membershipCard;

    // Referenced table
    @OneToMany(mappedBy = "libraryMember", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonManagedReference
    private List<BorrowRecord> borrowedBooks;
}
