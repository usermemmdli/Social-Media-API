package com.example.SocialMedia_API.service;

import com.example.SocialMedia_API.dao.entity.Post;
import com.example.SocialMedia_API.dao.entity.Users;
import com.example.SocialMedia_API.dao.repository.mongo.*;
import com.example.SocialMedia_API.dao.repository.postgres.UsersRepository;
import com.example.SocialMedia_API.dto.request.AccountDeleteRequest;
import com.example.SocialMedia_API.dto.request.AccountEditRequest;
import com.example.SocialMedia_API.dto.request.AccountPasswordEditRequest;
import com.example.SocialMedia_API.dto.response.AccountResponse;
import com.example.SocialMedia_API.dto.response.PostResponse;
import com.example.SocialMedia_API.dto.response.UsersResponse;
import com.example.SocialMedia_API.dto.response.pagination.PostPageResponse;
import com.example.SocialMedia_API.exception.BadRequestException;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.mapper.PostMapper;
import com.example.SocialMedia_API.mapper.UsersMapper;
import com.example.SocialMedia_API.security.AuthenticationHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final PostRepository postRepository;
    private final BookmarksRepository bookmarksRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final ReportRepository reportRepository;
    private final UsersMapper usersMapper;
    private final PostMapper postMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationHelperService authHelperService;

    public AccountResponse getAccountDetails() throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Integer countFollowersId = user.getFollowersId().size();
        Integer countFollowingId = user.getFollowingId().size();

        return AccountResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .profilePhotoUrl(user.getProfilePhotoUrl())
                .countFollowersId(countFollowersId)
                .countFollowingId(countFollowingId)
                .createdAt(user.getCreatedAt())
                .build();
    }

    public List<UsersResponse> getFollowingAccounts() throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        List<Users> usersList = usersRepository.findAllById(user.getFollowingId());
        return usersList.stream()
                .map(usersMapper::toUsersResponse)
                .toList();
    }

    public List<UsersResponse> getFollowersAccounts() throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        List<Users> usersList = usersRepository.findAllById(user.getFollowersId());
        return usersList.stream()
                .map(usersMapper::toUsersResponse)
                .toList();
    }

    public PostPageResponse getLikedPosts(int page, int count) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Pageable pageable = PageRequest.of(page, count);
        Page<Post> likedPosts = postRepository.findByUserId(user.getId(), pageable);
        List<PostResponse> postResponseList = likedPosts.getContent().stream()
                .filter(post -> postRepository.existsByIdIn(user.getLikedPostsId()))
                .map(postMapper::toPostResponse)
                .toList();

        return new PostPageResponse(
                postResponseList,
                likedPosts.getTotalElements(),
                likedPosts.getTotalPages(),
                likedPosts.hasNext());
    }

    public void deleteFollower(Long id) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));
        if (!user.getFollowersId().contains(id)) {
            throw new BadRequestException("User is not following this user");
        }
        user.getFollowersId().remove(id);
        usersRepository.save(user);
    }

    public void editAccountDetails(AccountEditRequest accountEditRequest) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));
        if (usersRepository.existsByUsername(accountEditRequest.getUsername()) && accountEditRequest.getUsername() != null) {
            throw new BadRequestException("Username is already taken");
        }
        user.setUsername(accountEditRequest.getUsername());
        user.setFullName(accountEditRequest.getFullName());
        if (usersRepository.existsByEmail(accountEditRequest.getEmail()) && accountEditRequest.getEmail() != null) {
            throw new BadRequestException("Email is already taken");
        }
        user.setEmail(accountEditRequest.getEmail());
        user.setProfilePhotoUrl(accountEditRequest.getProfilePhotoUrl());
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        usersRepository.save(user);
    }

    public void editAccountPassword(AccountPasswordEditRequest passwordEditRequest) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        if (!passwordEncoder.matches(passwordEditRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(passwordEditRequest.getNewPassword()));
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        usersRepository.save(user);
    }

    public void accountDelete(AccountDeleteRequest accountDeleteRequest) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        if (!passwordEncoder.matches(accountDeleteRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Password is incorrect");
        }
        usersRepository.deleteById(user.getId());
        if (postRepository.existsByUserId(user.getId())) {
            postRepository.deleteByUserId(user.getId());
        }
        if (bookmarksRepository.existsByUserId(user.getId())) {
            bookmarksRepository.deleteByUserId(user.getId());
        }
        if (commentRepository.existsByUserId(user.getId())) {
            commentRepository.deleteByUserId(user.getId());
        }
        if (likeRepository.existsByUserId(user.getId())) {
            likeRepository.deleteByUserId(user.getId());
        }
        if (reportRepository.existsByUserId(user.getId())) {
            reportRepository.deleteByUserId(user.getId());
        }
    }
}
