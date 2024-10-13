package com.security.basic.domain.entity.user.repository;

import com.security.basic.domain.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findUserByUsername(String username);
}
