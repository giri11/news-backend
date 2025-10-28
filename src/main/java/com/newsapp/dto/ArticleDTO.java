package com.newsapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleDTO {

    private Long id;
    private Long categoryId;
    private String categoryName;
    private String title;
    private String content;
    private String description;
    private String pathImage;
    private String createdByName;
    private String updatedByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String author;

}
