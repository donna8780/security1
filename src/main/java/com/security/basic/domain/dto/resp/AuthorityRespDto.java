package com.security.basic.domain.dto.resp;

import lombok.Builder;

@Builder
public record AuthorityRespDto(
        String name
) {
}
