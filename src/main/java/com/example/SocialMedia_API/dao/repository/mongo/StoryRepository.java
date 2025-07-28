package com.example.SocialMedia_API.dao.repository.mongo;

import com.example.SocialMedia_API.dao.entity.Story;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoryRepository extends MongoRepository<Story, String> {
    boolean existsByUserId(Long userId);

    Optional<Story> findByUserId(Long userId);
}
