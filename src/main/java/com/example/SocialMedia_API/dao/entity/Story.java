package com.example.SocialMedia_API.dao.entity;

import jakarta.persistence.Id;
import jdk.jfr.Enabled;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "story")
@Enabled
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Story {
    @Id
    String id;
    Long userId;
    String content;
    String imageUrl;
    Date expiredAt;
    Date createdAt;
}
