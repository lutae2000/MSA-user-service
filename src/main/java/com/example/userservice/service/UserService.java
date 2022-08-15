package com.example.userservice.service;

import com.example.userservice.UserDto.UserDto;
import com.example.userservice.jpa.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(String id);
    Iterable<UserEntity> getUserByAll();

}
