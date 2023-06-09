package ru.apiexternal.rest.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.api.security.AuthenticationUserDto;
import ru.apiexternal.entity.security.AccountUser;
import ru.apiexternal.exeption.InvalidUsernameOrPasswordException;
import ru.apiexternal.security.jwt.JwtTokenProvider;
import ru.apiexternal.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationRestController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationUserDto authenticationUserDto) {
        Map<Object, Object> response = new HashMap<>();
        try {
            final String username = authenticationUserDto.getUsername();
            AccountUser accountUser = userService.findByUsernameAndPassword(authenticationUserDto);

//            AccountUser accountUser = userService.findByUsername(username);

            String token = jwtTokenProvider.createToken(username, accountUser.getRoles());

//            Map<Object, Object> response = new HashMap<>();

            response.put("username", username);
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (InvalidUsernameOrPasswordException e) {
//        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
//            throw new BadCredentialsException("Invalid username or password.");
        }
    }
}
