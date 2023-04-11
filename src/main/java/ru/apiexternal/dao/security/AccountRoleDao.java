package ru.apiexternal.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.apiexternal.entity.security.AccountRole;

public interface AccountRoleDao extends JpaRepository<AccountRole, Long> {
    AccountRole findByName(String name);
}
