package com.newsapp.repository;

import com.newsapp.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("""
            select a from Article a 
            left join fetch a.category c 
            where a.id = :id 
            """)
    Article findByArticleId(@Param("id") Long id);

    @Query("""
            select a from Article a 
            left join fetch a.category c 
            where a.category.id = :id 
            """)
    List<Article> findByCategoryId(@Param("id") Long id);

    @Query("""
            select a from Article a 
            left join fetch a.category c 
            left join fetch a.createdBy cb 
            left join fetch a.updatedBy ub 
            where 1=1
            and ( :category is null or c.name = :category )
            """)
    Page<Article> findAll(Pageable pageable, @Param("category") String category);

}
