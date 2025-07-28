package com.example.SocialMedia_API.controller;

import com.example.SocialMedia_API.dto.request.AccountDeleteRequest;
import com.example.SocialMedia_API.dto.request.AccountEditRequest;
import com.example.SocialMedia_API.dto.request.AccountPasswordEditRequest;
import com.example.SocialMedia_API.dto.response.AccountResponse;
import com.example.SocialMedia_API.dto.response.UsersResponse;
import com.example.SocialMedia_API.dto.response.pagination.PostPageResponse;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/account/details")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AccountResponse> getAccountDetails() throws NotFoundException {
        AccountResponse accountResponse = usersService.getAccountDetails();
        return ResponseEntity.ok(accountResponse);
    }

    @GetMapping("/following")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UsersResponse>> getFollowingAccounts() throws NotFoundException {
        List<UsersResponse> usersList = usersService.getFollowingAccounts();
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/followers")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UsersResponse>> getFollowersAccounts() throws NotFoundException {
        List<UsersResponse> usersList = usersService.getFollowersAccounts();
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/account/liked-posts")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostPageResponse> getLikedPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "count", defaultValue = "9") int count) throws NotFoundException {
        PostPageResponse postResponse = usersService.getLikedPosts(page, count);
        return ResponseEntity.ok(postResponse);
    }

    @PostMapping("/followers/delete")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteFollower(@RequestParam Long id) throws NotFoundException {
        usersService.deleteFollower(id);
    }

    @PutMapping("/account/edit")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void editAccountDetails(@RequestBody AccountEditRequest accountEditRequest) throws NotFoundException {
        usersService.editAccountDetails(accountEditRequest);
    }

    @PatchMapping("/account/password")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void editAccountPassword(@RequestBody AccountPasswordEditRequest passwordEditRequest) throws NotFoundException {
        usersService.editAccountPassword(passwordEditRequest);
    }

    @DeleteMapping("/account/delete")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void accountDelete(@RequestBody AccountDeleteRequest accountDeleteRequest) throws NotFoundException {
        usersService.accountDelete(accountDeleteRequest);
    }
}
