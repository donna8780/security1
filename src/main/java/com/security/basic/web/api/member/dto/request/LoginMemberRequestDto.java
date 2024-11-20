package com.security.basic.web.api.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginMemberRequestDto(
    @NotBlank
    String email,
    @NotBlank
    String password
) {


}
//로그인 요청에 필요한 email과 password를 담는 불변 객체
//각 필드는 낫블랭크로 빈 값이나 공백을 허용하지 않도록 유효성 검증이 이루어 짐