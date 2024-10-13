package com.security.basic.domain.entity.authority.repository;

import com.security.basic.domain.entity.authority.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityJpaRepository extends JpaRepository<AuthorityEntity, Integer> {
}
