package com.newsapp.dto;

import lombok.Data;
import java.util.List;

@Data
public class RoleRequest {
    private String name;
    private String description;
    private List<Long> menuIds;
}
