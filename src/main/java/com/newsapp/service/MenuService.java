package com.newsapp.service;

import com.newsapp.dto.MenuDto;
import com.newsapp.model.Menu;
import com.newsapp.model.User;
import com.newsapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<MenuDto> getUserMenus() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get all menus from user roles
        Set<Menu> allMenus = user.getRoles().stream()
                .flatMap(role -> role.getMenus().stream())
                .filter(Menu::getActive)
                .collect(Collectors.toSet());

        // Also include all children of allowed menus
        Set<Menu> menusWithChildren = new HashSet<>(allMenus);
        for (Menu menu : allMenus) {
            addAllChildren(menu, menusWithChildren);
        }

        // Get root menus (menus without parent)
        List<Menu> rootMenus = menusWithChildren.stream()
                .filter(menu -> menu.getParent() == null)
                .sorted(Comparator.comparing(Menu::getOrder))
                .toList();

        return rootMenus.stream()
                .map(menu -> convertToDto(menu, menusWithChildren))
                .collect(Collectors.toList());
    }

    private void addAllChildren(Menu menu, Set<Menu> menuSet) {
        if (menu.getSubMenus() != null && !menu.getSubMenus().isEmpty()) {
            for (Menu child : menu.getSubMenus()) {
                if (child.getActive()) {
                    menuSet.add(child);
                    addAllChildren(child, menuSet);
                }
            }
        }
    }

    private MenuDto convertToDto(Menu menu, Set<Menu> allMenus) {
        MenuDto dto = new MenuDto();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setIcon(menu.getIcon());
        dto.setPath(menu.getPath());
        dto.setOrder(menu.getOrder());

        // Get submenu DTOs
        List<MenuDto> subMenus = new ArrayList<>();
        if (menu.getSubMenus() != null && !menu.getSubMenus().isEmpty()) {
            subMenus = menu.getSubMenus().stream()
                    .filter(Menu::getActive)
                    .filter(allMenus::contains)
                    .sorted(Comparator.comparing(Menu::getOrder))
                    .map(sub -> convertToDto(sub, allMenus))
                    .collect(Collectors.toList());
        }

        dto.setSubMenus(subMenus);
        return dto;
    }
}
