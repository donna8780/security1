package com.security.basic.domain.entity.authority.repository;

import com.security.basic.domain.dto.resp.AuthorityRespDto;
import com.security.basic.domain.entity.authority.AuthorityEntity;
import com.security.basic.domain.entity.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class AuthorityRepository {
    private final AuthorityJpaRepository authorityJpaRepository;

    @Transactional
    public AuthorityRespDto create(String name, UserEntity userEntity) {
        return authorityJpaRepository.save(new AuthorityEntity(name, userEntity))
                .toAuthority();
    }
}
