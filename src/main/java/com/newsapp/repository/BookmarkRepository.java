package com.newsapp.repository;

import com.newsapp.model.Bookmark;
import com.newsapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUserOrderByBookmarkedAtDesc(User user);
    Optional<Bookmark> findByUserAndArticleUrl(User user, String articleUrl);
    boolean existsByUserAndArticleUrl(User user, String articleUrl);
}