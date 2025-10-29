package com.newsapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('dashboard')")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(Map.of(
                "totalUsers", 1234,
                "totalOrders", 567,
                "revenue", 45678,
                "products", 89
        ));
    }
}
