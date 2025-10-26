package com.newsapp.dto;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class MenuDto {
    private Long id;
    private String name;
    private String icon;
    private String path;
    private Integer order;
    private List<MenuDto> subMenus = new ArrayList<>();
}
