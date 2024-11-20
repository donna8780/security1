package com.security.basic.global.handler;

import com.security.basic.global.enums.ResponseCode;
import com.security.basic.web.api.member.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice //컨트롤러에서 발생한 예외를 이 클래스에서 처리
public class CustomExceptionHandler {

  @ExceptionHandler(Exception.class)//특정 예외 발생 시 이 메서드 호출
  public final ResponseEntity<Response<String>> handleAllExceptions(Exception ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new Response<String>(ResponseCode.GENERAL_ERROR, ex.getMessage()));
  }
}
/*전체 흐름
  1. 예외 발생: 애플리케이션에서 예외가 발생하면 Exception이 잡힌다
  2. 예외 처리 : CustomExceptionHandler 클래스의 handleAllExceptions메서드가 호출
  3. 응답 생성 : ResponseEntity를 사용하여 HTTP 응답을 생성
                상태 코드 500
                본문: ResponseCode.GENERAL_ERROR와 예외 메시지가 포함된 JSON객체
  4. JSON응답 반환: 클라이언트에게 JSON 형태로 에러 응답이 반환
 요약: 이 코드는 모든 예외를 잡에서 처리하고, 일관된 에러 응답을 JSON 형식으로 클라이언ㄴ트에 반환하는 기능

예시 응답
만약 예외가 발생하면 클라이언트는 다음과 같은 JSON 형식의 응답을 받게 됩니다:

{
  "code": "GENERAL_ERROR",
  "message": "에러 메시지"
}
 */