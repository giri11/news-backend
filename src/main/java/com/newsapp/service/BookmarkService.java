package com.newsapp.service;

import com.newsapp.dto.BookmarkRequest;
import com.newsapp.dto.BookmarkResponse;
import com.newsapp.model.Bookmark;
import com.newsapp.model.User;
import com.newsapp.repository.BookmarkRepository;
import com.newsapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public BookmarkResponse addBookmark(BookmarkRequest request) {
        User user = getCurrentUser();

        if (bookmarkRepository.existsByUserAndArticleUrl(user, request.getArticleUrl())) {
            throw new RuntimeException("Article already bookmarked");
        }

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .articleTitle(request.getArticleTitle())
                .articleUrl(request.getArticleUrl())
                .articleImage(request.getArticleImage())
                .articleDescription(request.getArticleDescription())
                .source(request.getSource())
                .publishedAt(request.getPublishedAt())
                .build();

        bookmark = bookmarkRepository.save(bookmark);

        return mapToResponse(bookmark);
    }

    public List<BookmarkResponse> getUserBookmarks() {
        User user = getCurrentUser();
        List<Bookmark> bookmarks = bookmarkRepository.findByUserOrderByBookmarkedAtDesc(user);
        return bookmarks.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void removeBookmark(Long bookmarkId) {
        User user = getCurrentUser();
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new RuntimeException("Bookmark not found"));

        if (!bookmark.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        bookmarkRepository.delete(bookmark);
    }

    public boolean isBookmarked(String articleUrl) {
        User user = getCurrentUser();
        return bookmarkRepository.existsByUserAndArticleUrl(user, articleUrl);
    }

    private BookmarkResponse mapToResponse(Bookmark bookmark) {
        return BookmarkResponse.builder()
                .id(bookmark.getId())
                .articleTitle(bookmark.getArticleTitle())
                .articleUrl(bookmark.getArticleUrl())
                .articleImage(bookmark.getArticleImage())
                .articleDescription(bookmark.getArticleDescription())
                .source(bookmark.getSource())
                .publishedAt(bookmark.getPublishedAt())
                .bookmarkedAt(bookmark.getBookmarkedAt())
                .build();
    }
}