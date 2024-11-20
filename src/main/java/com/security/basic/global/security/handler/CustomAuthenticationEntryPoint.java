package com.security.basic.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.basic.global.dto.ErrorResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
//용자가 누구인지 확인하는 과정.
//예: 로그인 요청에서 제공된 토큰이 유효한지 확인.
//인증 실패란:
//토큰이 없거나 잘못된 경우.
//사용자가 인증을 하지 않았을 때.
//CustomAuthenticationEntryPoint는 이러한 인증 실패 상황을 처리

@Slf4j(topic = "UNAUTHORIZATION_EXCEPTION_HANDLER")
@AllArgsConstructor//클래스에 선언된 모든 필드를 매개변수로 받는 생성자를 자동으로 생성
@Component//이 클래스가 Spring 컨테이너에 의해 관리되는 **빈(Bean)**임을 나타냅니다
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  //CustomAuthenticationEntryPoint 클래스
  //AuthenticationEntryPoint 인터페이스를 구현합니다.
  //Spring Security에서 인증되지 않은 사용자가 보호된 리소스에 접근하려 할 때 실행
  private final ObjectMapper objectMapper;

  @Override
  //이 메서드는 인증되지 않은 사용자가 요청을 보낼 때 호출된다. 주요 작업: 로그 기록, 에러 응답 생성 및 전송
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    log.error("Not Authenticated Request", authException);
    //1.로그 기록: 인증되지 않은 요청이 발생했음을 로그로 남긴다. authException객체에는 인증 실패의 상세 정보가 포함
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.UNAUTHORIZED, authException.getMessage(), LocalDateTime.now());
    //2.에러 응답 객체 생성: ErrorResponseDto객체를 생성, HttpStatus.UNAUTHORIZED: HTTP 상태 코드 401을 나타냄
    //authException.getMessage(): 인증 실패 이유를 가져옴
    //LocalDateTime.now(): 에러 발생 시점을 기록
    String responseBody = objectMapper.writeValueAsString(errorResponseDto);
    //3.응답 본문(JSON)생성 : Jackson의 ObjectMapper를 사용하여 ErrorResponseDto객체를 JSON문자열로 변환
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(responseBody);
    /*
    Content-Type: application/json으로 설정.
    Status: 401(Unauthorized).
    Character-Encoding: UTF-8로 설정.
    Body: 변환된 JSON 문자열(responseBody)을 응답에 추가.
    */
  }
}
/*
예시 응답 JSON
이 클래스가 처리한 결과로 클라이언트는 다음과 같은 JSON 응답을 받습니다:

{
  "status": "UNAUTHORIZED",
  "message": "Full authentication is required to access this resource",
  "timestamp": "2024-11-20T12:34:56"
}

목적: 인증되지 않은 요청을 처리하며, 에러 메시지를 JSON 형식으로 반환합니다.
주요 작업:
로그를 기록합니다.
ErrorResponseDto를 JSON 형식으로 변환해 클라이언트에게 응답으로 보냅니다.
이 코드는 API 요청이 인증되지 않은 상태일 때 사용자 친화적인 JSON 응답을 제공하는 역할
* */