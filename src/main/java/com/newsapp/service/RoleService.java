package com.newsapp.service;
import com.newsapp.dto.MenuDto;
import com.newsapp.dto.RoleDto;
import com.newsapp.dto.RoleRequest;
import com.newsapp.model.Menu;
import com.newsapp.model.Role;
import com.newsapp.model.User;
import com.newsapp.repository.MenuRepository;
import com.newsapp.repository.RoleRepository;
import com.newsapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RoleDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        return convertToDto(role);
    }

    @Transactional
    public RoleDto createRole(RoleRequest request) {
        // Check if role name already exists
        if (roleRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("Role with name '" + request.getName() + "' already exists");
        }

        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());

        // Set menus
        if (request.getMenuIds() != null && !request.getMenuIds().isEmpty()) {
            Set<Menu> menus = new HashSet<>();
            for (Long menuId : request.getMenuIds()) {
                Menu menu = menuRepository.findById(menuId)
                        .orElseThrow(() -> new RuntimeException("Menu not found with id: " + menuId));
                menus.add(menu);
            }
            role.setMenus(menus);
        }

        Role savedRole = roleRepository.save(role);
        return convertToDto(savedRole);
    }

    @Transactional
    public RoleDto updateRole(Long id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

        // Check if new name conflicts with existing role
        if (!role.getName().equals(request.getName())) {
            if (roleRepository.findByName(request.getName()).isPresent()) {
                throw new RuntimeException("Role with name '" + request.getName() + "' already exists");
            }
            role.setName(request.getName());
        }

        role.setDescription(request.getDescription());

        // Update menus
        role.getMenus().clear();
        if (request.getMenuIds() != null && !request.getMenuIds().isEmpty()) {
            Set<Menu> menus = new HashSet<>();
            for (Long menuId : request.getMenuIds()) {
                Menu menu = menuRepository.findById(menuId)
                        .orElseThrow(() -> new RuntimeException("Menu not found with id: " + menuId));
                menus.add(menu);
            }
            role.setMenus(menus);
        }

        Role updatedRole = roleRepository.save(role);
        return convertToDto(updatedRole);
    }

    @Transactional
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

        // Check if role is assigned to any users
        List<User> usersWithRole = userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(role))
                .toList();

        if (!usersWithRole.isEmpty()) {
            throw new RuntimeException("Cannot delete role. It is assigned to " +
                    usersWithRole.size() + " user(s)");
        }

        roleRepository.delete(role);
    }

    @Transactional(readOnly = true)
    public List<MenuDto> getAllAvailableMenus() {
        List<Menu> allMenus = menuRepository.findAll();

        // Get root menus
        List<Menu> rootMenus = allMenus.stream()
                .filter(menu -> menu.getParent() == null && menu.getActive())
                .sorted(Comparator.comparing(Menu::getOrder))
                .toList();

        return rootMenus.stream()
                .map(this::convertMenuToDto)
                .collect(Collectors.toList());
    }

    private RoleDto convertToDto(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());

        // Convert menus
        List<MenuDto> menuDtos = role.getMenus().stream()
                .filter(Menu::getActive)
                .filter(menu -> menu.getParent() == null)
                .sorted(Comparator.comparing(Menu::getOrder))
                .map(this::convertMenuToDto)
                .collect(Collectors.toList());
        dto.setMenus(menuDtos);

        // Count users with this role
        long userCount = userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(role))
                .count();
        dto.setUserCount((int) userCount);

        return dto;
    }

    private MenuDto convertMenuToDto(Menu menu) {
        MenuDto dto = new MenuDto();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setIcon(menu.getIcon());
        dto.setPath(menu.getPath());
        dto.setOrder(menu.getOrder());

        // Convert submenus
        if (menu.getSubMenus() != null && !menu.getSubMenus().isEmpty()) {
            List<MenuDto> subMenus = menu.getSubMenus().stream()
                    .filter(Menu::getActive)
                    .sorted(Comparator.comparing(Menu::getOrder))
                    .map(this::convertMenuToDto)
                    .collect(Collectors.toList());
            dto.setSubMenus(subMenus);
        }

        return dto;
    }
}
