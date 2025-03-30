package org.example.libraryspringapi.service.auth;

import org.example.libraryspringapi.entity.LibraryMember;
import org.example.libraryspringapi.entity.auth.CustomUserDetails;
import org.example.libraryspringapi.entity.auth.LibraryUser;
import org.example.libraryspringapi.repository.LibraryMemberRepo;
import org.example.libraryspringapi.repository.auth.LibraryUserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
public class LibraryUserService implements UserDetailsService {

    private LibraryUserRepo libraryUserRepo;
    private LibraryMemberRepo libraryMemberRepo;

    // Access repos with constructor DI
    public LibraryUserService(LibraryUserRepo libraryUserRepo, LibraryMemberRepo libraryMemberRepo) {
        this.libraryUserRepo = libraryUserRepo;
        this.libraryMemberRepo = libraryMemberRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user in the database
        Optional<LibraryUser> libraryUser = libraryUserRepo.findByUsername(username);
        if (libraryUser.isPresent()) {
            LibraryUser user = libraryUser.get();
            Long libraryMemberId = user.getLibraryMember().getId();
            // Convert roles into authorities for custom user details object
            Collection<? extends GrantedAuthority> authorities = Arrays.stream(getRoles(user))
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .toList();

            CustomUserDetails details = new CustomUserDetails(user.getUsername(), user.getPassword(), libraryMemberId, authorities);
            // Print out library member ID and associated username to console
            System.out.println(details.getLibraryMemberId() + " " + details.getUsername());
            // Return custom user details object that includes library member ID, comment out the next line if using default User.builder
            return details;

            // Comment out to use regular User.builder - disables use of library member ID for restricting endpoint access
//            return User.builder()
//                    .username(user.getUsername())
//                    .password(user.getPassword())
//                    .roles(getRoles(user))
//                    .build();
        }
        return null;
    }

    // Method to parse out user roles, separated by commas
    private String[] getRoles(LibraryUser libraryUser) {
        if (libraryUser.getRole() == null) {
            return new String[] {"MEMBER"};
        }
        return libraryUser.getRole().split(",");
    }

    public Optional<LibraryUser> addLibraryUser(Long memberId, LibraryUser libraryUser) {
        LibraryMember libraryMember = libraryMemberRepo.findById(memberId).orElse(null);
        if (libraryMember != null) {
            libraryUser.setLibraryMember(libraryMember);
            libraryUserRepo.save(libraryUser);
            // Return the new library user after saving
            return Optional.of(libraryUser);
        }
        // Return empty if library member is not found
        return Optional.empty();
    }

    // Update a library user
    public LibraryUser updateLibraryUser(Long id, LibraryUser libraryUser) {
        LibraryUser existingLibraryUser = libraryUserRepo.findById(id).orElse(null);
        if (existingLibraryUser != null) {
            existingLibraryUser.setUsername(libraryUser.getUsername());
            // Re-encode the incoming password with bcrypt
            String password = new BCryptPasswordEncoder().encode(libraryUser.getPassword());
            existingLibraryUser.setPassword(password);
            if (libraryUser.getRole() != null) {
                // Update the user's role if it is included
                existingLibraryUser.setRole(libraryUser.getRole());
            }
        }
        return libraryUserRepo.save(existingLibraryUser);
    }

    // Delete library user by ID
    public void deleteLibraryUser(Long id) {
        libraryUserRepo.deleteById(id);
    }
}
