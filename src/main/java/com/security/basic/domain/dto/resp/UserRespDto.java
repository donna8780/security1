package com.security.basic.domain.dto.resp;

import com.security.basic.enums.EncryptionAlgorithm;
import lombok.Builder;

import java.util.List;

@Builder
public record UserRespDto(
        String username,
        String password,
        EncryptionAlgorithm algorithm,
        List<AuthorityRespDto> authorities
) {
}
