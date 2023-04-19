package ru.apiexternal.service;

import ru.api.security.AuthenticationUserDto;
import ru.api.security.UserDto;
import ru.apiexternal.entity.security.AccountUser;

import java.util.List;

public interface UserService {

    UserDto register(UserDto userDto);
    List<UserDto> findAll();
    UserDto findByName(String name);
    AccountUser findByUsername(String username);
    UserDto findById(Long id);
    UserDto update(UserDto userDto);
    void deleteById(Long id);

    AccountUser findByUsernameAndPassword(AuthenticationUserDto authenticationUserDto);

}
