package com.newsapp.service;

import com.newsapp.dto.UserDto;
import com.newsapp.dto.UserRequest;
import com.newsapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserDto> getAllUsers(Pageable pageable, String search);
    UserDto getUserById(Long id);
    UserDto createUser(UserRequest request);
    UserDto updateUser(Long id, UserRequest request);
    void deleteUser(Long id);

}
