package com.newsapp.controller;

import com.newsapp.dto.BookmarkRequest;
import com.newsapp.dto.BookmarkResponse;
import com.newsapp.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Bookmarks", description = "Bookmark management APIs (Requires Authentication)")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(
            summary = "Add a bookmark",
            description = "Save an article to user's bookmarks"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Bookmark added successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookmarkResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT token required"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Article already bookmarked"
            )
    })
    @PostMapping
    public ResponseEntity<BookmarkResponse> addBookmark(@RequestBody BookmarkRequest request) {
        return ResponseEntity.ok(bookmarkService.addBookmark(request));
    }

    @Operation(
            summary = "Get all bookmarks",
            description = "Retrieve all bookmarks for the authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved bookmarks"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT token required"
            )
    })
    @GetMapping
    public ResponseEntity<List<BookmarkResponse>> getUserBookmarks() {
        return ResponseEntity.ok(bookmarkService.getUserBookmarks());
    }

    @Operation(
            summary = "Remove a bookmark",
            description = "Delete a bookmark by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Bookmark deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT token required"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bookmark not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBookmark(@PathVariable Long id) {
        bookmarkService.removeBookmark(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Check if article is bookmarked",
            description = "Check if a specific article URL is already bookmarked by the user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully checked bookmark status"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT token required"
            )
    })
    @GetMapping("/check")
    public ResponseEntity<Boolean> isBookmarked(@RequestParam String articleUrl) {
        return ResponseEntity.ok(bookmarkService.isBookmarked(articleUrl));
    }
}