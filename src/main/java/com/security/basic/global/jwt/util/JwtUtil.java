package com.security.basic.global.jwt.util;

import com.security.basic.domain.member.service.dto.CustomUserInfoDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j //로그 기록을 위한 롬복 어노테이션
@Component
public class JwtUtil {
  private final Key key;//서명 키, secretKey를 HMAC-SHA256 알고리즘에 사용할 수 있도록 변환한 객체
  private final long accessTokenExpTime;//엑세스 토큰 만료 시간

  public JwtUtil(
      @Value("${spring.jwt.secret.key}") String secretKey, // application.yml에 설정된 값
      @Value("${spring.jwt.expiration_time}") long accessTokenExpTime// 토큰 만료 시간
  ) {
    byte[] decodeKey = Decoders.BASE64.decode(secretKey); // Base64로 디코딩
    this.key = Keys.hmacShaKeyFor(decodeKey); // HMAC-SHA256 키 생성
    this.accessTokenExpTime = accessTokenExpTime;// 만료 시간 설정
  }

  /**
   * Access Token 생성
   * @param member
   * @return Access Token String
   */
  public String createAccessToken(CustomUserInfoDto member) {
    return createToken(member, accessTokenExpTime);
  }//CustomUserInfoDto는 사용자 정보를 담은 DTO, createToken 메서드를 호출하여 사용자 정보와 만료 시간을 기반으로 JWT를 생성

  private String createToken(CustomUserInfoDto member, long expireTime) {
    Claims claims = Jwts.claims();// JWT의 클레임(Claims) 생성
    claims.put("memberId", member.memberId()); // 사용자 ID 추가
    claims.put("email", member.email());// 이메일 추가
    claims.put("role", member.role());// 역할(Role) 추가

    ZonedDateTime now = ZonedDateTime.now();// 현재 시간
    ZonedDateTime tokenValidity = now.plusSeconds(expireTime);// 만료 시간 계산

    return Jwts.builder()
        .setClaims(claims)// 클레임 정보 설정
        .setIssuedAt(Date.from(now.toInstant()))// 토큰 발급 시간
        .setExpiration(Date.from(tokenValidity.toInstant()))// 만료 시간
        .signWith(key, SignatureAlgorithm.HS256)// 서명 설정
        .compact();// 토큰 생성 및 반환
  }

  public String getUserId(String token) {
    return parseClaims(token).get("email", String.class);
  } // 토큰의 "email" 값을 반환

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()//토큰을 파싱하여 검증합니다. 서명이 유효한지, 만료되지 않았는지 등을 확인합니다
          .setSigningKey(key) // 서명 키 설정
          .build()
          .parseClaimsJws(token); // 토큰 검증
      return true; // 검증 성공
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT Token", e);// 위조된 토큰
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT Token", e);// 만료된 토큰
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT Token", e);// 지원하지 않는 토큰
    } catch (IllegalArgumentException e) {
      log.info("JWT claims string is empty.", e);// 비어 있는 토큰
    }
    return false;// 검증 실패
  }

  public Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(accessToken)
          .getBody();// 토큰의 클레임 반환
    } catch (ExpiredJwtException e) {
      return e.getClaims(); // 만료된 토큰도 클레임 반환
    }
  }
}
