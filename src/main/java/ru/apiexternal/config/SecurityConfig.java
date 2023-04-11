package ru.apiexternal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    public static final String USER_ENDPOINT = "/api/v1/user";
    public static final String REGISTRATION_ENDPOINT = "/api/v1/auth/register";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> {
            requests.antMatchers("/").permitAll();
//            requests.antMatchers("/authorize").permitAll();
//            requests.antMatchers("/product/all").permitAll();
//            requests.antMatchers("/product").hasRole("ADMIN");
////            requests.antMatchers(HttpMethod.GET, "/product").hasRole("ADMIN");
////            requests.antMatchers(HttpMethod.POST, "/product").hasRole("ADMIN");
//            requests.mvcMatchers(HttpMethod.GET, "/product/{productId}").permitAll();
//            requests.anyRequest().authenticated();
        });

        http.httpBasic().disable();
        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

