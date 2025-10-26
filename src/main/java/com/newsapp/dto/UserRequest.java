package com.newsapp.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String name;
    private String email;
    private List<Long> roleIds;
    private Boolean active;
}
