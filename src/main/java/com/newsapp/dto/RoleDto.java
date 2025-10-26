package com.newsapp.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleDto {

    private Long id;
    private String name;
    private String description;
    private List<MenuDto> menus = new ArrayList<>();
    private Integer userCount;

}
