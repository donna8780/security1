package com.security.basic.global.enums;

public enum ResponseCode {
  LOGIN_SUCCESS("로그인 성공"),
  SIGNUP_SUCCESS("회원가입 성공"),
  GENERAL_ERROR("에러 발생");

  private final String message;

  ResponseCode(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
/*역할 요약
  ResponseCode 열거형은 응답 코드와 관련된 메시지를 중앙에서 관리
  코드에서 메시지를 일관되게 사용하도록 돕고, 하드코딩을 방지
  상수 형태로 응답 메시지를 정의하여 코드의 가독성과 유지보수성을 높임
 */