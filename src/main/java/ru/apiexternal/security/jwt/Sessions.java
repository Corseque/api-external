package ru.apiexternal.security.jwt;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Sessions {
    private final Map<String, String> sessionToken = new HashMap<>();
}
