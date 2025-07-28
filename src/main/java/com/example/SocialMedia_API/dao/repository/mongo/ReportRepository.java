package com.example.SocialMedia_API.dao.repository.mongo;

import com.example.SocialMedia_API.dao.entity.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
    boolean existsByUserId(Long id);

    void deleteByUserId(Long id);
}
