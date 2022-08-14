package com.example.userservice.service;

import com.example.userservice.UserDto.UserDto;
import com.example.userservice.jpa.UserEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(String id);
    Iterable<UserEntity> getUserByAll();

}
