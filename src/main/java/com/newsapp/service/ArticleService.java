package com.newsapp.service;
import com.newsapp.dto.ArticleDTO;
import com.newsapp.dto.ArticleRequest;
import com.newsapp.model.Article;
import com.newsapp.model.Category;
import com.newsapp.model.User;
import com.newsapp.repository.ArticleRepository;
import com.newsapp.repository.CategoryRepository;
import com.newsapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public Page<ArticleDTO> getAllArticles(Pageable pageable, String search) {
        Page<Article> articles;

        if (search != null && !search.trim().isEmpty()) {
            articles = articleRepository.searchArticles(search.trim(), pageable);
        } else {
            articles = articleRepository.findAll(pageable);
        }

        return articles.map(this::convertToDto);
    }

    @Transactional
    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + id));
        return convertToDto(article);
    }

    @Transactional
    public ArticleDTO createArticle(ArticleRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));

        Article article = new Article();
        article.setCategory(category);
        article.setTitle(request.getTitle());
        article.setDescription(request.getDescription());
        article.setContent(request.getContent());
        article.setPathImage(request.getPathImage());
        article.setCreatedBy(currentUser);

        Article savedArticle = articleRepository.save(article);
        return convertToDto(savedArticle);
    }

    @Transactional
    public ArticleDTO updateArticle(Long id, ArticleRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + id));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));

        article.setCategory(category);
        article.setTitle(request.getTitle());
        article.setDescription(request.getDescription());
        article.setContent(request.getContent());
        article.setPathImage(request.getPathImage());
        article.setUpdatedBy(currentUser);

        Article updatedArticle = articleRepository.save(article);
        return convertToDto(updatedArticle);
    }

    @Transactional
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + id));
        articleRepository.delete(article);
    }

    private ArticleDTO convertToDto(Article article) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(article.getId());
        dto.setCategoryId(article.getCategory() != null ? article.getCategory().getId() : null);
        dto.setCategoryName(article.getCategory() != null ? article.getCategory().getName() : null);
        dto.setTitle(article.getTitle());
        dto.setDescription(article.getDescription());
        dto.setContent(article.getContent());
        dto.setPathImage(article.getPathImage());
        dto.setCreatedAt(article.getCreatedAt());
        dto.setUpdatedAt(article.getUpdatedAt());
        dto.setCreatedByName(article.getCreatedBy() != null ? article.getCreatedBy().getName() : null);
        dto.setUpdatedByName(article.getUpdatedBy() != null ? article.getUpdatedBy().getName() : null);
        return dto;
    }

}
