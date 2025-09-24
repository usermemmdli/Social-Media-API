package com.example.SocialMedia_API.service;

import com.example.SocialMedia_API.dao.entity.Bookmarks;
import com.example.SocialMedia_API.dao.entity.Post;
import com.example.SocialMedia_API.dao.entity.Users;
import com.example.SocialMedia_API.dao.repository.mongo.BookmarksRepository;
import com.example.SocialMedia_API.dao.repository.mongo.PostRepository;
import com.example.SocialMedia_API.dao.repository.postgres.UsersRepository;
import com.example.SocialMedia_API.dto.response.PostResponse;
import com.example.SocialMedia_API.dto.response.pagination.PostPageResponse;
import com.example.SocialMedia_API.exception.BadRequestException;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.mapper.PostMapper;
import com.example.SocialMedia_API.security.AuthenticationHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarksService {
    private final BookmarksRepository bookmarksRepository;
    private final UsersRepository usersRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AuthenticationHelperService authHelperService;

    public PostPageResponse getMarkedPosts(int page, int count) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Bookmarks userBookmarks = bookmarksRepository.findByUserId(user.getId());

        Pageable pageable = PageRequest.of(page, count);
        Page<Post> markedPosts = postRepository.findByUserId(user.getId(), pageable);
        List<PostResponse> postResponseList = markedPosts.getContent().stream()
                .filter(post -> postRepository.existsByIdIn(userBookmarks.getPostId()))
                .map(postMapper::toPostResponse)
                .toList();

        return new PostPageResponse(
                postResponseList,
                markedPosts.getTotalElements(),
                markedPosts.getTotalPages(),
                markedPosts.hasNext());
    }

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
