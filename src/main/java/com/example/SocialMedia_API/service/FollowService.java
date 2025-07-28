package com.example.SocialMedia_API.service;

import com.example.SocialMedia_API.dao.entity.Users;
import com.example.SocialMedia_API.dao.repository.postgres.UsersRepository;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.security.AuthenticationHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UsersRepository usersRepository;
    private final AuthenticationHelperService authHelperService;

    public void followUser(Long id) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        user.getFollowingId().add(id);
        usersRepository.save(user);
    }

    public void unfollowUser(Long id) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        user.getFollowingId().remove(id);
        usersRepository.save(user);
    }
}
