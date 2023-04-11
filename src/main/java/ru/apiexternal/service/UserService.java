package ru.apiexternal.service;

import ru.api.security.UserDto;

import java.util.List;

public interface UserService {

    UserDto register(UserDto userDto);
    List<UserDto> findAll();
    UserDto findByName(String name);
    UserDto findById(Long id);
    UserDto update(UserDto userDto);
    void deleteById(Long id);


}
