package com.newsapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menus")
@Data
@ToString(exclude = {"parent", "subMenus"})
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String icon;

    private String path;

    @Column(name = "menu_order")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("order ASC")
    private List<Menu> subMenus = new ArrayList<>();

    private Boolean active = true;

    @Column(name = "menu_access")
    private String menuAccess;
}
