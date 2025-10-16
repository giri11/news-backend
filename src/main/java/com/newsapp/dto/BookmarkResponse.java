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
public class BookmarkResponse {
    private Long id;
    private String articleTitle;
    private String articleUrl;
    private String articleImage;
    private String articleDescription;
    private String source;
    private LocalDateTime publishedAt;
    private LocalDateTime bookmarkedAt;
}