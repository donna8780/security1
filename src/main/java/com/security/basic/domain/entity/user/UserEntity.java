package com.security.basic.domain.entity.user;

import com.security.basic.domain.dto.req.create.CreateUserReqDto;
import com.security.basic.enums.EncryptionAlgorithm;
import com.security.basic.domain.dto.resp.UserRespDto;
import com.security.basic.domain.entity.authority.AuthorityEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private EncryptionAlgorithm algorithm;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<AuthorityEntity> authorities;

    public UserEntity(String username, String password, EncryptionAlgorithm algorithm) {
        this.username = username;
        this.password = password;
        this.algorithm = algorithm;
    }

    public UserRespDto toDto() {
        return UserRespDto.builder()
                .username(this.username)
                .password(this.password)
                .algorithm(this.algorithm)
                .authorities(this.authorities.stream().map(AuthorityEntity::toAuthority).toList())
                .build();
    }

    public void replaceAuthority(List<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

    public static UserEntity newUser(CreateUserReqDto create) {
        return new UserEntity(
                create.username(),
                create.password(),
                EncryptionAlgorithm.BCRYPT
        );
    }
}
