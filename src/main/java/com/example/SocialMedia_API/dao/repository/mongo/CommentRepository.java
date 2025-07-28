package com.example.SocialMedia_API.dao.repository.mongo;

import com.example.SocialMedia_API.dao.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    boolean existsByUserId(Long id);

    void deleteByUserId(Long id);
}
