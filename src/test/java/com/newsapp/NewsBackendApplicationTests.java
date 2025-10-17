package com.newsapp;

import com.newsapp.model.Article;
import com.newsapp.model.Category;
import com.newsapp.model.Role;
import com.newsapp.model.User;
import com.newsapp.service.NewsService;
import com.newsapp.service.UserService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
@DataJpaTest
class NewsBackendApplicationTests {

	@Autowired
	private NewsService newsService;

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		// This test just checks if Spring context loads

	}

}