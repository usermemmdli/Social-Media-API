package com.example.SocialMedia_API.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jdk.jfr.Enabled;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Entity
@Builder
@Document(collection = "post")
@Enabled
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    @Id
    String id;
    Long userId;
    List<String> commentId;
    String content;
    Integer commentCount;
    Integer likeCount;
    String imageUrl;
    Date createdAt;
    Date updatedAt;
}
