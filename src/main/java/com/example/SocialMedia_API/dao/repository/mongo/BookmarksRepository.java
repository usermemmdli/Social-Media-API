package com.example.SocialMedia_API.dao.repository.mongo;

import com.example.SocialMedia_API.dao.entity.Bookmarks;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarksRepository extends MongoRepository<Bookmarks, String> {
    Bookmarks findByUserId(Long userId);

    boolean existsByUserId(Long id);

    void deleteByUserId(Long userId);
}
