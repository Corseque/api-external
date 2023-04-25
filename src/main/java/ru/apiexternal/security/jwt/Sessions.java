package ru.apiexternal.security.jwt;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sessions {
    private final Map<HttpSession, String> sessionToken = new HashMap<>();
}
