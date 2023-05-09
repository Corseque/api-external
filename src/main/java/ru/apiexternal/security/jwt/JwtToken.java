package ru.apiexternal.security.jwt;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtToken {
    private String token;
}
