package com.newsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkRequest {
    private String articleTitle;
    private String articleUrl;
    private String articleImage;
    private String articleDescription;
    private String source;
    private LocalDateTime publishedAt;
}