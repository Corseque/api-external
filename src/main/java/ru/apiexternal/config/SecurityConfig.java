package ru.apiexternal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ru.apiexternal.security.jwt.JwtConfigurer;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    public static final String USER_ENDPOINT = "/api/v1/user";
    public static final String REGISTRATION_ENDPOINT = "/api/v1/auth/register";

    private final JwtConfigurer jwtConfigurer;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests((requests) -> {
            requests.antMatchers(LOGIN_ENDPOINT).permitAll();
            requests.antMatchers(REGISTRATION_ENDPOINT).permitAll();
            requests.anyRequest().authenticated();
        });

        http.apply(jwtConfigurer);
        http.httpBasic().disable();
        http.csrf().disable();
    }



}

