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

@Document(collection = "report")
@Enabled
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Report {
    @Id
    String id;
    String description;
    Long reporterUserId;
    Long userId;
    String postId;
    String commentId;
    Boolean isUser;
    Boolean isPost;
    Boolean isComment;
    Boolean status;
    Date createdAt;
    Date updatedAt;
}
