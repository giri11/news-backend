package com.newsapp.controller;

import com.newsapp.dto.MenuDto;
import com.newsapp.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MenuDto>> getUserMenus() {
        return ResponseEntity.ok(menuService.getUserMenus());
    }
}
