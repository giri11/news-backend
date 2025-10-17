package com.newsapp.service;

import com.newsapp.dto.CategoryDTO;
import com.newsapp.model.Article;
import com.newsapp.model.Category;

import java.util.List;

public interface NewsService {

    Category save(Category category);
    Article save(Article article);

    List<CategoryDTO> getAllActiveCategories();

}
