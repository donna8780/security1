package com.security.basic.domain.dto.req.create;

public record CreateUserReqDto(
        String username,
        String password
) {
}
