package com.example.mytech.model.dto;

import com.example.mytech.entity.Blog;
import com.example.mytech.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BlogDTO {
    private String id;
    private String title;
    private String slug;
    private String content;
    private String description;
    private int status;
    private String image;
    private Timestamp publishedAt;
    private Timestamp modifiedAt;
    private String createBy;
    private String modifiedBY;

    public BlogDTO(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.slug = blog.getSlug();
        this.description = blog.getDescription();
        this.content = blog.getContent();
        this.image = blog.getImage();
        this.status = blog.getStatus();
        this.modifiedAt = blog.getModifiedAt();
        this.publishedAt = blog.getPublishedAt();
        this.createBy = blog.getCreatedBy().getName();
    }
}