package org.example.libraryspringapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "The membership card issue date is required!")
    @Column(nullable = false)
    private Date issueDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "The membership card expiry date is required!")
    @Column(nullable = false)
    private Date expiryDate;

    // Relationship owned by LibraryMember
    // Since we don't want membershipCard to persist when a LibraryMember is deleted
    // don't specify any cascade types here
    @OneToOne(mappedBy = "membershipCard")
    @JsonBackReference(value = "member-card")
    private LibraryMember libraryMember;

    public MembershipCard() {
    }

    public MembershipCard(Date issueDate, String cardNumber, Date expiryDate) {
        this.issueDate = issueDate;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public void setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
    }

    @Override
    public String toString() {
        return "MembershipCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", issueDate=" + issueDate +
                ", expiryDate=" + expiryDate +
                '}';
    }
}