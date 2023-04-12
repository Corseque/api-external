package ru.apiexternal.rest.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.api.security.UserDto;
import ru.apiexternal.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserRestController {

    private final UserService userService;

    @GetMapping("/all")
    public List<UserDto> getUserList() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable(name = "userId") Long id) {
        if (id != null) {
            UserDto userDto = userService.findById(id);
            if (userDto.getId() != null) {

                return new ResponseEntity<>(userDto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<?> getUserByUsername(@Validated @RequestBody UserDto userDto) {
        if (userDto.getUsername() != null) {
            UserDto userFromDB = userService.findByName(userDto.getUsername());
            if (userFromDB.getId() != null) {
                return new ResponseEntity<>(userFromDB, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@Validated @RequestBody UserDto userDto) {
        UserDto savedUser = userService.register(userDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/user/" + savedUser.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "userId") Long id,
                                                @Validated @RequestBody UserDto userDto) {
        userDto.setId(id);
        UserDto savedUserDto = userService.update(userDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/user/" + savedUserDto.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "userId") Long id) {
        userService.deleteById(id);
    }
}
