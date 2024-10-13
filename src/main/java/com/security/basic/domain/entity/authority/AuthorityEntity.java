package com.security.basic.domain.entity.authority;

import com.security.basic.domain.dto.resp.AuthorityRespDto;
import com.security.basic.domain.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "authorities")
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @JoinColumn(name = "userEntity")
    @ManyToOne
    private UserEntity userEntity;

    public AuthorityEntity(String name, UserEntity userEntity) {
        this.name = name;
        this.userEntity = userEntity;
    }

    public AuthorityRespDto toAuthority() {
        return AuthorityRespDto.builder()
                .name(this.name)
                .build();
    }
}
