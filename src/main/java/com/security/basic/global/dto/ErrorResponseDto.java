package com.security.basic.global.dto;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public record ErrorResponseDto(
    HttpStatus status,
    String message,
    LocalDateTime errorDateTime
) {

}
/*ErrorResponseDto는 에러 정보를 저장하는 DTO로, CustomAuthenticationEntryPoint와 CustomAccessDeniedHandler에서 사용

이 클래스는 에러 응답의 구조를 정의하며, 클라이언트에게 JSON 형태로 에러 정보를 전달*/