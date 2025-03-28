package org.example.libraryspringapi.service.auth;

import org.example.libraryspringapi.entity.LibraryMember;
import org.example.libraryspringapi.entity.auth.CustomUserDetails;
import org.example.libraryspringapi.entity.auth.LibraryUser;
import org.example.libraryspringapi.repository.LibraryMemberRepo;
import org.example.libraryspringapi.repository.auth.LibraryUserRepo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Optional;

@Service
public class LibraryUserService implements UserDetailsService {

    private LibraryUserRepo libraryUserRepo;
    private LibraryMemberRepo libraryMemberRepo;

    public LibraryUserService(LibraryUserRepo libraryUserRepo, LibraryMemberRepo libraryMemberRepo) {
        this.libraryUserRepo = libraryUserRepo;
        this.libraryMemberRepo = libraryMemberRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LibraryUser> libraryUser = libraryUserRepo.findByUsername(username);
        if (libraryUser.isPresent()) {
            LibraryUser user = libraryUser.get();
            Long libraryMemberId = user.getLibraryMember().getId();
            Collection<? extends GrantedAuthority> authorities = Arrays.stream(getRoles(user))
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .toList();

            CustomUserDetails details = new CustomUserDetails(user.getUsername(), user.getPassword(), libraryMemberId, authorities);
            System.out.println(details.getLibraryMemberId() + " " + details.getUsername());

            return details;

//            return User.builder()
//                    .username(user.getUsername())
//                    .password(user.getPassword())
//                    .roles(getRoles(user))
//                    .build();
        }
        return null;
    }

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
            return Optional.of(libraryUser);
        }
        return Optional.empty();
    }

    public LibraryUser updateLibraryUser(Long id, LibraryUser libraryUser) {
        LibraryUser existingLibraryUser = libraryUserRepo.findById(id).orElse(null);
        if (existingLibraryUser != null) {
            existingLibraryUser.setUsername(libraryUser.getUsername());
            String password = new BCryptPasswordEncoder().encode(libraryUser.getPassword());
            existingLibraryUser.setPassword(password);
            existingLibraryUser.setRole(libraryUser.getRole());
        }
        return libraryUserRepo.save(existingLibraryUser);
    }

    public void deleteLibraryUser(Long id) {
        libraryUserRepo.deleteById(id);
    }
}
