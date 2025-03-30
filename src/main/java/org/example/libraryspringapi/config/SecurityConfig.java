package org.example.libraryspringapi.config;

import org.example.libraryspringapi.service.auth.LibraryUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// This annotation allows the @PreAuthorized annotation to work on individual endpoints
@EnableMethodSecurity
public class SecurityConfig {

    private LibraryUserService libraryUserService;

    public SecurityConfig(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    // Method to return LibraryUserService object to define user details
    @Bean
    public UserDetailsService userDetailsService() {
        return libraryUserService;
    }

    // Configure the authentication provider for Spring Security
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Set user details service class
        authProvider.setUserDetailsService(userDetailsService());
        // Set password encoder to bcrypt
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }

    // Configure security and permitted roles for Spring Security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(securityConfigurer ->
                securityConfigurer
                        // Restrict access to register librarians, edit and delete users to admins
                        .requestMatchers(HttpMethod.POST, "/register-librarian/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN")
                        // Restrict access to manage books and authors, view borrow records to admins and librarians
                        .requestMatchers(HttpMethod.GET, "/api/borrow-record").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.GET, "/api/borrow-record/*").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/api/book/new/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.PUT, "/api/book/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.DELETE, "/api/book/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/api/author/new").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.PUT, "/api/author/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.DELETE, "/api/author/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        // Restrict access to manage borrow records and view members to librarians
                        .requestMatchers(HttpMethod.GET, "/api/library-member").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.GET, "/api/library-member/*").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.PUT, "/api/borrow-record/*").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.DELETE, "/api/borrow-record/*").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/api/borrow-record/*/add-to-member/**").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/api/borrow-record/*/remove-from-member/**").hasRole("LIBRARIAN")
                        // Restrict access to check out or return books, and access borrow records by member
                        // to members and librarians
                        .requestMatchers(HttpMethod.POST, "/api/borrow-record/*/check-out/**").hasAnyRole("LIBRARIAN", "MEMBER")
                        .requestMatchers(HttpMethod.POST, "/api/borrow-record/*/return/**").hasAnyRole("LIBRARIAN", "MEMBER")
                        .requestMatchers(HttpMethod.GET, "/api/borrow-record/history/**").hasAnyRole("LIBRARIAN", "MEMBER")
                        // Allow all requests without authentication to register as a member and get available books
                        .requestMatchers("/register-member/**").permitAll()
                        .requestMatchers("/api/book/available").permitAll()
                        // Set all non-specified endpoints to require an authenticated user
                        .anyRequest().authenticated()
        );

        // Set the application to use basic authentication
        httpSecurity.httpBasic(Customizer.withDefaults());
        // Disable CSRF token
        httpSecurity.csrf(csrfConfigurer -> csrfConfigurer.disable());
        return httpSecurity.build();
    }
}
