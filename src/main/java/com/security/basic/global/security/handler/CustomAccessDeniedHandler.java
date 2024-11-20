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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
//권한 부족 처리 : 관리자만 접근 가능한 API에 일반 사용자가 접근
@Slf4j(topic = "FORBIDDEN_EXCEPTION_HANDLER")
@AllArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {

    log.error("No Authorities", accessDeniedException);
    //1.로그 기록: 권한 부족으로 요청이 거부된 사실을 로그에 기록
    //accessDeniedException에는 권한 거부에 대한 자세한 정보가 포함되어 있음

    ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.FORBIDDEN, accessDeniedException.getMessage(), LocalDateTime.now());
    //2. 에러 응답 객체 생성 : ErrorResponseDto 객체를 생성
    String responseBody = objectMapper.writeValueAsString(errorResponseDto);
    //Jackson의 **ObjectMapper**를 사용해 ErrorResponseDto 객체를 JSON 문자열로 변환.
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(responseBody);
  }
}
/*예시 응답 JSON
이 핸들러가 처리한 결과로 클라이언트는 다음과 같은 JSON 응답을 받습니다:

{
  "status": "FORBIDDEN",
  "message": "Access is denied",
  "timestamp": "2024-11-20T12:34:56"
}
status: HTTP 상태 코드 설명.
message: 권한 부족 메시지.
timestamp: 에러 발생 시각.*/
