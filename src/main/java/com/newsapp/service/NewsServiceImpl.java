package com.newsapp.service;

import com.newsapp.model.Article;
import com.newsapp.model.Category;
import com.newsapp.repository.ArticleRepository;
import com.newsapp.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }
}
