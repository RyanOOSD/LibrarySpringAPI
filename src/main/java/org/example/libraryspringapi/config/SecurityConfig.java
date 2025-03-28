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
@EnableMethodSecurity
public class SecurityConfig {

    private LibraryUserService libraryUserService;

    public SecurityConfig(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return libraryUserService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(securityConfigurer ->
                securityConfigurer
                        .requestMatchers(HttpMethod.POST, "/register-librarian/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/borrow-record").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.GET, "/api/borrow-record/*").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/api/book/new/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.PUT, "/api/book/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.DELETE, "/api/book/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/api/author/new").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.PUT, "/api/author/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.DELETE, "/api/author/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.GET, "/api/library-member").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.GET, "/api/library-member/*").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.PUT, "/api/borrow-record/*").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.DELETE, "/api/borrow-record/*").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/api/borrow-record/*/add-to-member/**").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/api/borrow-record/*/remove-from-member/**").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/api/borrow-record/*/check-out/**").hasAnyRole("LIBRARIAN", "MEMBER")
                        .requestMatchers(HttpMethod.POST, "/api/borrow-record/*/return/**").hasAnyRole("LIBRARIAN", "MEMBER")
                        .requestMatchers(HttpMethod.GET, "/api/borrow-record/history/**").hasAnyRole("LIBRARIAN", "MEMBER")
                        .requestMatchers("/register-member/**").permitAll()
                        .requestMatchers("/api/book/available").permitAll()
                        .anyRequest().authenticated()
        );

        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(csrfConfigurer -> csrfConfigurer.disable());
        return httpSecurity.build();
    }
}
