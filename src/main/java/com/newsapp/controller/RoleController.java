package com.newsapp.controller;

import com.newsapp.dto.MenuDto;
import com.newsapp.dto.RoleDto;
import com.newsapp.dto.RoleRequest;
import com.newsapp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('roles')")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('roles')")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('roles')")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleService.createRole(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('roles')")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long id,
                                              @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('roles')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available-menus")
    @PreAuthorize("hasRole('roles')")
    public ResponseEntity<List<MenuDto>> getAvailableMenus() {
        return ResponseEntity.ok(roleService.getAllAvailableMenus());
    }
}
