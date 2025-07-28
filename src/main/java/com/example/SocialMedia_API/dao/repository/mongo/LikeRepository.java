package com.example.SocialMedia_API.dao.repository.mongo;

import com.example.SocialMedia_API.dao.entity.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {
    Optional<Like> findByPostId(String postId);

    void deleteByPostId(String postId);

    boolean existsByUserId(Long id);

    void deleteByUserId(Long id);
}
