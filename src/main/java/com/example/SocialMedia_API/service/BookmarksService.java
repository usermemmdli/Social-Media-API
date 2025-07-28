package com.example.SocialMedia_API.service;

import com.example.SocialMedia_API.dao.entity.Bookmarks;
import com.example.SocialMedia_API.dao.entity.Users;
import com.example.SocialMedia_API.dao.repository.mongo.BookmarksRepository;
import com.example.SocialMedia_API.dao.repository.postgres.UsersRepository;
import com.example.SocialMedia_API.exception.BadRequestException;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.security.AuthenticationHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarksService {
    private final BookmarksRepository bookmarksRepository;
    private final UsersRepository usersRepository;
    private final AuthenticationHelperService authHelperService;

    public void addBookmarks(String postId) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Bookmarks bookmarks = bookmarksRepository.findByUserId(user.getId());
        if (bookmarks.getPostId().contains(postId)) {
            throw new BadRequestException("You already bookmarked this post");
        }
        bookmarks.getPostId().add(postId);
        bookmarksRepository.save(bookmarks);
    }

    public void deleteBookmarks(String postId) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Bookmarks bookmarks = bookmarksRepository.findByUserId(user.getId());
        if (!bookmarks.getPostId().contains(postId)) {
            throw new BadRequestException("You dont bookmarked this post");
        }
        bookmarks.getPostId().remove(postId);
        bookmarksRepository.save(bookmarks);
    }
}
