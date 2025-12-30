package com.newsapp.config;

import com.newsapp.model.Menu;
import com.newsapp.model.Role;
import com.newsapp.model.User;
import com.newsapp.repository.MenuRepository;
import com.newsapp.repository.RoleRepository;
import com.newsapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MenuRepository menuRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        // Create Menus - Parent menus first
//        Menu dashboard = createMenu("Dashboard", "Home", "/dashboard", 1, null, "dashboard");
//        Menu users = createMenu("Users", "Users", "/users", 2, null, "users");
//        Menu roles = createMenu("Roles", "Shield", "/roles", 3, null, "roles");
//        Menu products = createMenu("Products", "Package", "/products", 4, null, "products");
//        Menu orders = createMenu("Orders", "ShoppingCart", "/orders", 5, null, "orders");
//        Menu reports = createMenu("Reports", "FileText", null, 6, null, "reports");
//        Menu settings = createMenu("Settings", "Settings", null, 7, null, "settings");
//
//        // Save parent menus first
//        menuRepository.saveAll(Arrays.asList(dashboard, users, roles, products, orders, reports, settings));
//        menuRepository.flush();
//
//        // Create submenus and associate with parent
//        Menu salesReport = createMenu("Sales Report", "BarChart2", "/reports/sales", 1, reports, "reports_sales");
//        Menu inventoryReport = createMenu("Inventory Report", "Package", "/reports/inventory", 2, reports, "reports_inventory");
//        Menu profileSettings = createMenu("Profile", "Users", "/settings/profile", 1, settings, "settings_profile");
//        Menu systemSettings = createMenu("System", "Settings", "/settings/system", 2, settings, "settings_system");
//
//        // Save submenus
//        menuRepository.saveAll(Arrays.asList(salesReport, inventoryReport, profileSettings, systemSettings));
//        menuRepository.flush();
//
//        // Explicitly set submenus to parent
//        reports.getSubMenus().add(salesReport);
//        reports.getSubMenus().add(inventoryReport);
//        settings.getSubMenus().add(profileSettings);
//        settings.getSubMenus().add(systemSettings);
//
//        menuRepository.saveAll(Arrays.asList(reports, settings));
//        menuRepository.flush();
//
//        // Create Roles
//        Role adminRole = new Role();
//        adminRole.setName("ADMIN");
//        adminRole.setDescription("Administrator with full access");
//        adminRole.setMenus(new HashSet<>(Arrays.asList(
//                dashboard, users, roles, products, orders, reports, settings
//        )));
//
//        Role userRole = new Role();
//        userRole.setName("USER");
//        userRole.setDescription("Regular User with limited access");
//        userRole.setMenus(new HashSet<>(Arrays.asList(dashboard, products, orders)));
//
//        roleRepository.saveAll(Arrays.asList(adminRole, userRole));
//        roleRepository.flush();
//
//        // Create Users
//        User admin = new User();
//        admin.setUsername("admin");
//        admin.setPassword(passwordEncoder.encode("admin123"));
//        admin.setName("Admin User");
//        admin.setEmail("admin@example.com");
//        admin.setRoles(new HashSet<>(Arrays.asList(adminRole)));
//
//        User user = new User();
//        user.setUsername("user");
//        user.setPassword(passwordEncoder.encode("user123"));
//        user.setName("Regular User");
//        user.setEmail("user@example.com");
//        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
//
//        userRepository.saveAll(Arrays.asList(admin, user));
//
//        Menu categories = createMenu("Categories", "FolderOpen", "/categories", 4, null, "categories");
//        Menu articles = createMenu("Articles", "FileText", "/articles", 5, null, "articles");
//
//        menuRepository.saveAll(Arrays.asList(categories, articles));

        System.out.println("========================================");
        System.out.println("Database initialized with sample data!");
        System.out.println("========================================");
        System.out.println("Login credentials:");
        System.out.println("Admin - username: admin, password: admin123");
        System.out.println("User  - username: user, password: user123");
        System.out.println("========================================");
        System.out.println("Menus created: Dashboard, Users, Roles, Products, Orders, Reports, Settings");
        System.out.println("========================================");
    }

    private Menu createMenu(String name, String icon, String path, int order, Menu parent, String menuAccess) {
        Menu menu = new Menu();
        menu.setName(name);
        menu.setIcon(icon);
        menu.setPath(path);
        menu.setOrder(order);
        menu.setParent(parent);
        menu.setActive(true);
        menu.setMenuAccess(menuAccess);
        return menu;
    }
}

