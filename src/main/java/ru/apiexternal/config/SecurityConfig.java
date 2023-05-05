package ru.apiexternal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.apiexternal.security.jwt.JwtTokenFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    public static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    public static final String USER_ENDPOINT = "/api/v1/user";
//    public static final String REGISTRATION_ENDPOINT = "/api/v1/auth/register";
    public static final String REGISTRATION_ENDPOINT = "/api/v1/user/register";
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http.authorizeRequests((requests) -> {
            requests.antMatchers(LOGIN_ENDPOINT).permitAll();
            requests.antMatchers(REGISTRATION_ENDPOINT).permitAll();
            requests.anyRequest().authenticated();
        });
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic().disable();
        http.csrf().disable();
        return http.build();
    }


}

