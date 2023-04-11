package ru.apiexternal.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.apiexternal.entity.security.Authority;

public interface AuthorityDao extends JpaRepository<Authority, Long> {
}
