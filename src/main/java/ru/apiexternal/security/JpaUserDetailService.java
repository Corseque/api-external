package ru.apiexternal.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.api.security.UserDto;
import ru.apiexternal.dao.security.AccountRoleDao;
import ru.apiexternal.dao.security.AccountUserDao;
import ru.apiexternal.entity.security.AccountRole;
import ru.apiexternal.entity.security.AccountUser;
import ru.apiexternal.entity.security.enums.AccountStatus;
import ru.apiexternal.exeption.UserNotFoundException;
import ru.apiexternal.exeption.UsernameAlreadyExistsException;
import ru.apiexternal.rest.mapper.UserMapper;
import ru.apiexternal.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class JpaUserDetailService implements UserDetailsService, UserService {

    private final AccountUserDao accountUserDao;
    private final AccountRoleDao accountRoleDao;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountUserDao.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username: " + username + " not found.")
        );
    }

    @Override
    public UserDto register(UserDto userDto) {
        if (accountUserDao.findByUsername(userDto.getUsername()).isPresent()) {
                throw new UsernameAlreadyExistsException(
                        String.format("User with username %s already exists", userDto.getUsername()));
        }
        AccountUser accountUser = userMapper.toAccountUser(userDto);
        AccountRole roleUser = accountRoleDao.findByName("ROLE_USER");
        accountUser.setRoles(Set.of(roleUser));
        accountUser.setStatus(AccountStatus.ACTIVE);
        accountUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        AccountUser registeredAccountUser = accountUserDao.save(accountUser);
        log.debug("User with username {} was registered successfully", registeredAccountUser.getUsername());
        return userMapper.toUserDto(registeredAccountUser);
    }

    public UserDto update(UserDto userDto) {
        AccountUser accountUser = userMapper.toAccountUser(userDto);
        if (accountUser.getId() != null) {
            accountUserDao.findById(accountUser.getId()).ifPresent(u -> {
                accountUser.setVersion(u.getVersion());
                accountUser.setStatus(u.getStatus());
            });
        }
        return userMapper.toUserDto(accountUserDao.save(accountUser));
    }

    private AccountUser update(AccountUser accountUser) {
        if (accountUser.getId() != null) {
            accountUserDao.findById(accountUser.getId()).ifPresent(m ->
                accountUser.setVersion(m.getVersion())
            );
        }
        return accountUserDao.save(accountUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return accountUserDao.findAll().stream()
                .map(user -> userMapper.toUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findByName(String name) {
        return userMapper.toUserDto(accountUserDao.findByUsername(name)
                .orElseThrow(() -> new NoSuchElementException("There is no user with name " + name)));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        return userMapper.toUserDto(accountUserDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("There is no user with id " + id)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        final AccountUser accountUser = accountUserDao.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User with id %s not found", id))
        );
        disable(accountUser);
        update(accountUser);
    }

    private void enable(final AccountUser accountUser) {
        accountUser.setStatus(AccountStatus.ACTIVE);
        accountUser.setAccountNonLocked(true);
        accountUser.setAccountNonExpired(true);
        accountUser.setEnabled(true);
        accountUser.setCredentialsNonExpired(true);
    }

    private void disable(final AccountUser accountUser) {
        accountUser.setStatus(AccountStatus.DELETED);
        accountUser.setAccountNonLocked(false);
        accountUser.setAccountNonExpired(false);
        accountUser.setEnabled(false);
        accountUser.setCredentialsNonExpired(false);
    }
}
