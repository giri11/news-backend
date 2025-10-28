package com.newsapp.dto;

import lombok.Data;

@Data
public class ArticleRequest {
    private Long categoryId;
    private String title;
    private String description;
    private String content;
    private String pathImage;

}
