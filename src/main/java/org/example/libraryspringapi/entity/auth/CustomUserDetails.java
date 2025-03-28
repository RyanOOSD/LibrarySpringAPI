package org.example.libraryspringapi.entity.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private Long libraryMemberId;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, Long libraryMemberId, Collection<? extends GrantedAuthority> authorities  ) {
        this.username = username;
        this.password = password;
        this.libraryMemberId = libraryMemberId;
        this.authorities = authorities;
    }

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

    public Long getLibraryMemberId() {
        return libraryMemberId;
    }
}
