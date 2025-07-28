package com.example.SocialMedia_API.security;

import com.example.SocialMedia_API.dao.entity.Users;
import com.example.SocialMedia_API.dao.repository.postgres.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUsersDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(users.getUsername())
                .password(users.getPassword())
                .authorities("ROLE_" + users.getRoles().getName())
                .build();
    }
}

