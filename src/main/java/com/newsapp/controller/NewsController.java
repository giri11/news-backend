package com.newsapp.controller;

import com.newsapp.dto.ArticleDTO;
import com.newsapp.dto.CategoryDTO;
import com.newsapp.dto.PagingResponseDTO;
import com.newsapp.model.Article;
import com.newsapp.model.Category;
import com.newsapp.model.User;
import com.newsapp.repository.ArticleRepository;
import com.newsapp.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Tag(name = "news", description = "News List API")
public class NewsController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private NewsService newsService;

    @Operation(
            summary = "Get all news",
            description = "Retrieve all news"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved bookmarks"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Key"
            )
    })
    @GetMapping
    public ResponseEntity<List<?>> listNews(){
        return ResponseEntity.ok(null);
    }

    @Operation(
            summary = "Get Top Headline news",
            description = "Retrieve Top Headline news"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved Top Headline"
            )
    })
    @GetMapping("/top-headlines")
    public ResponseEntity<PagingResponseDTO> topHeadlines(@RequestParam(value = "category", required = false) String category,
                                                                @RequestParam("pageSize") Integer pageSize){
        log.debug("param "+category);

        try{
            Pageable pageable = PageRequest.of(0, pageSize, Sort.by("a.createdAt").descending());
            Page<Article> page = articleRepository.findAll(pageable, category);
            List<ArticleDTO> contents = new ArrayList<>();
            for(Article article : page.getContent()){
                ArticleDTO dto = new ArticleDTO();
                BeanUtils.copyProperties(article, dto);
                dto.setAuthor(article.getCreatedBy().getName());
                contents.add(dto);
            }

            PagingResponseDTO respDTO = new PagingResponseDTO();
            respDTO.setStatus(HttpStatus.OK.name());
            respDTO.setTotalResults(page.getTotalElements());
            respDTO.setTotalPages(page.getTotalPages());
            respDTO.setData(contents);

            return ResponseEntity.ok(respDTO);
        } catch (Exception e) {
            log.error("Error "+e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
            summary = "Get Detail news",
            description = "Retrieve Detail news"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved Top Headline"
            )
    })
    @GetMapping("/detail/{id}")
    public ResponseEntity<Article> detail(
            @RequestHeader Map<String, String> headers,
            @PathVariable(name = "id", required = true) Long id){
        log.debug("header "+headers.get("x-api-key"));
        log.debug("param "+id);
        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value));
        });
        String resp = "{\n" +
                "      \"source\": {\n" +
                "        \"id\": null,\n" +
                "        \"name\": \"BaltimoreRavens.com\"\n" +
                "      },\n" +
                "      \"author\": null,\n" +
                "      \"title\": \"Pundits Question Whether Ravens Hit ‘Rock Bottom’ With Loss to Texans | Late for Work - Baltimore Ravens\",\n" +
                "      \"description\": \"Another struggling offense gets right against the Ravens defense. The Ravens offense faltered without Lamar Jackson. Pundits say the Ravens’ season isn’t lost yet.\",\n" +
                "      \"url\": \"https://www.baltimoreravens.com/news/ravens-texans-week-5-reaction-rock-bottom-loss-injuries-lamar-jackson-playoff-race\",\n" +
                "      \"urlToImage\": \"https://static.clubs.nfl.com/image/upload/t_editorial_landscape_12_desktop/ravens/tir9tqakmacdz52bhqti\",\n" +
                "      \"createdAt\": \"2025-10-06T13:46:24Z\",\n" +
                "      \"content\": null\n" +
                "    }\n" ;
        log.debug("response "+resp);

        Category catSport = new Category();
        catSport.setId(2L);
        catSport.setName("Sport");

        User userBarron = new User();
        userBarron.setId(1L);
        userBarron.setName("Barron");

        Article article = new Article();
        article.setId(1L);
        article.setCategory(catSport);
        article.setTitle("Giri AMD Stock: Why Shares Are Soaring on the OpenAI News Today - Barron's");
        article.setCreatedBy(userBarron);
        article.setCreatedAt(LocalDateTime.now());
        article.setDescription("About AMD Stock: Why Shares Are Soaring on the OpenAI News Today - Barron's");
        article.setPathImage("https://pub-5b247ec389ff4be5994c335b26363bc5.r2.dev/1-1757870070188419600");
        article.setContent("Why Shares Are Soaring on the OpenAI News Today \n 1Why Shares Are Soaring on the OpenAI News Today" +
                "\n 3Why Shares Are Soaring on the OpenAI News Today");

        return ResponseEntity.ok(article);
    }

    @Operation(
            summary = "Get Categories",
            description = "Retrieve Categories"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved Categories"
            )
    })
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getCategories() {

        log.debug("masuk sini");
        List<CategoryDTO> categories = newsService.getAllActiveCategories();
        return ResponseEntity.ok(categories);
    }

}
