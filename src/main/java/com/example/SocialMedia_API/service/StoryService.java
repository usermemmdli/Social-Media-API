package com.example.SocialMedia_API.service;

import com.example.SocialMedia_API.dao.entity.Story;
import com.example.SocialMedia_API.dao.entity.Users;
import com.example.SocialMedia_API.dao.repository.mongo.StoryRepository;
import com.example.SocialMedia_API.dao.repository.postgres.UsersRepository;
import com.example.SocialMedia_API.dto.request.CreateStoryRequest;
import com.example.SocialMedia_API.dto.response.StoryResponse;
import com.example.SocialMedia_API.exception.BadRequestException;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.mapper.StoryMapper;
import com.example.SocialMedia_API.security.AuthenticationHelperService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class StoryService {
    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;
    private final UsersRepository usersRepository;
    private final AuthenticationHelperService authHelperService;

    public StoryResponse showMyStory() throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Story story = storyRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("Story not found with user id: " + user.getId()));
        if (story.getExpiredAt().before(Date.from(Instant.now()))) {
            storyRepository.deleteById(story.getId());
            throw new BadRequestException("Story has expired");
        }
        return storyMapper.toStoryResponse(story);
    }

    public StoryResponse createNewStory(CreateStoryRequest createStoryRequest) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Story story = new Story();
        story.setUserId(user.getId());
        story.setContent(createStoryRequest.getContent());
        story.setImageUrl(createStoryRequest.getImageUrl());
        story.setExpiredAt(DateUtils.addDays(Date.from(Instant.now()), 1));
        story.setCreatedAt(Date.from(Instant.now()));
        storyRepository.save(story);
        return storyMapper.toStoryResponse(story);
    }

    public void deleteStory(String id) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        if (!storyRepository.existsByUserId(user.getId()) && !storyRepository.existsById(id)) {
            throw new NotFoundException("Story not found with id: " + id);
        }
        storyRepository.deleteById(id);
    }
}
