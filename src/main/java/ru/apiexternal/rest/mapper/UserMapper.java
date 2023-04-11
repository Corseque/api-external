package ru.apiexternal.rest.mapper;

import org.mapstruct.Mapper;
import ru.api.security.UserDto;
import ru.apiexternal.entity.security.AccountUser;

@Mapper
public interface UserMapper {
    AccountUser toAccountUser(UserDto userDto);
    UserDto toUserDto(AccountUser accountUser);
}
