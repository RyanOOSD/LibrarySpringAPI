package org.example.libraryspringapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

@Entity
public class MembershipCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank(message = "The membership card number is required!")
    @Column(nullable = false, unique = true)
    private String cardNumber;

    // Formats incoming/outgoing JSON
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "The issue date must be in the format yyyy-MM-dd!")
    @NotNull(message = "The membership card issue date is required!")
    @Column(nullable = false)
    private Date issueDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "The expiry date must be in the format yyyy-MM-dd!")
    @NotNull(message = "The membership card expiry date is required!")
    @Column(nullable = false)
    private Date expiryDate;

    // Relationship owned by LibraryMember
    // Since we don't want membershipCard to persist when a LibraryMember is deleted
    // we don't specify any cascade types here
    @OneToOne(mappedBy = "membershipCard")
    @JsonBackReference
    private LibraryMember libraryMember;
}