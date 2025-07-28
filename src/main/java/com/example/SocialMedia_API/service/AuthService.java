package com.example.SocialMedia_API.service;

import com.example.SocialMedia_API.dao.entity.Bookmarks;
import com.example.SocialMedia_API.dao.entity.Roles;
import com.example.SocialMedia_API.dao.entity.Users;
import com.example.SocialMedia_API.dao.repository.mongo.BookmarksRepository;
import com.example.SocialMedia_API.dao.repository.postgres.RolesRepository;
import com.example.SocialMedia_API.dao.repository.postgres.UsersRepository;
import com.example.SocialMedia_API.dto.request.LoginRequest;
import com.example.SocialMedia_API.dto.request.SignUpRequest;
import com.example.SocialMedia_API.dto.response.JwtResponse;
import com.example.SocialMedia_API.exception.EmailAlreadyIsTakenException;
import com.example.SocialMedia_API.exception.InvalidEmailOrPasswordException;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;
    private final RolesRepository rolesRepository;
    private final BookmarksRepository bookmarksRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public JwtResponse loginUser(@Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Users users = usersRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new InvalidEmailOrPasswordException("Email or password is invalid"));
        String token = jwtService.createToken(users);
        return new JwtResponse(token);
    }

    public void signUpUser(@Valid SignUpRequest signUpRequest) throws NotFoundException {
        if (usersRepository.existsByUsername((signUpRequest.getUsername()))) {
            throw new EmailAlreadyIsTakenException("Email is already taken");
        }
        Users users = new Users();

        Roles defaultRole = rolesRepository.findByName(("USER"))
                .orElseThrow(() -> new NotFoundException("No roles found"));

        users.setRoles(defaultRole);
        users.setUsername(signUpRequest.getUsername());
        users.setFullName(signUpRequest.getFullName());
        users.setEmail(signUpRequest.getEmail());
        users.setProfilePhotoUrl(signUpRequest.getProfilePhotoUrl());
        users.setLikedPostsId(new ArrayList<>());
        users.setFollowersId(new ArrayList<>());
        users.setFollowingId(new ArrayList<>());
        users.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        users.setCreatedAt(Timestamp.from(Instant.now()));
        usersRepository.save(users);
        Bookmarks bookmarks = new Bookmarks();
        bookmarks.setPostId(new ArrayList<>());
        bookmarks.setUserId(users.getId());
        bookmarks.setCreatedAt(Date.from(Instant.now()));
        bookmarksRepository.save(bookmarks);
    }
}
