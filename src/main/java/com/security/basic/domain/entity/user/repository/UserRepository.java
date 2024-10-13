package com.security.basic.domain.entity.user.repository;

import com.security.basic.domain.dto.req.create.CreateUserReqDto;
import com.security.basic.domain.dto.resp.UserRespDto;
import com.security.basic.domain.entity.authority.AuthorityEntity;
import com.security.basic.domain.entity.authority.repository.AuthorityJpaRepository;
import com.security.basic.domain.entity.user.UserEntity;
import com.security.basic.global.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final AuthorityJpaRepository authorityJpaRepository;

    public Boolean userExists(String username) {
        return userJpaRepository.findUserByUsername(username).isPresent();
    }

    public UserRespDto getUserByUsername(String username) {
        return userJpaRepository.findUserByUsername(username)
                .orElseThrow(UserNotFoundException::new)
                .toDto();
    }

    @Transactional
    public UserRespDto create(CreateUserReqDto create) {
        UserEntity userEntity = userJpaRepository.save(UserEntity.newUser(create));
        AuthorityEntity authority = authorityJpaRepository.save(new AuthorityEntity("READ", userEntity));
        userEntity.replaceAuthority(List.of(authority));

        return userEntity.toDto();
    }

}
