package org.example.libraryspringapi.entity.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// Custom user details object created for Spring Security in order to include library member ID
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private Long libraryMemberId;
    private Collection<? extends GrantedAuthority> authorities;

    // Constructor
    public CustomUserDetails(String username, String password, Long libraryMemberId, Collection<? extends GrantedAuthority> authorities  ) {
        this.username = username;
        this.password = password;
        this.libraryMemberId = libraryMemberId;
        this.authorities = authorities;
    }

    // Required override methods if class implements UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Define getter for library member ID
    public Long getLibraryMemberId() {
        return libraryMemberId;
    }
}
