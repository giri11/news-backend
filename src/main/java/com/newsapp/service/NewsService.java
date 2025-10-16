package com.newsapp.service;

import com.newsapp.model.Article;
import com.newsapp.model.Category;

public interface NewsService {

    Category save(Category category);
    Article save(Article article);

}
