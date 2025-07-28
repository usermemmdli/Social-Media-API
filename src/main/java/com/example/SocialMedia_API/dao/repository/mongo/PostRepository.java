package com.example.SocialMedia_API.dao.repository.mongo;

import com.example.SocialMedia_API.dao.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findByUserId(Long userId, Pageable pageable);

    boolean existsByIdIn(List<String> likedPostsId);

    boolean existsByUserId(Long userId);

    void deleteByUserId(Long userId);
}
