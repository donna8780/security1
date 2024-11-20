package com.security.basic.global.jwt.filter;

import com.security.basic.global.jwt.util.JwtUtil;
import com.security.basic.global.security.member.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  private final CustomUserDetailsService customUserDetailsService; //사용자 정보를 로드하는 서비스. (주로 데이터베이스에서 사용자 정보 조회)
  private final JwtUtil jwtUtil; //JWT 생성, 검증, 사용자 정보 추출 등을 담당하는 유틸리티 클래스

  @Override //필터의 핵심 로직. 요청이 들어올 때마다 호출, 요청의 Authorization 헤더를 확인하고, 유효한 JWT가 있으면 인증 상태를 설정
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String authorization = request.getHeader("Authorization");
    //1.Authorization 헤더 확인:
    // 요청 헤더에서 authorization값을 가져오고, 헤더 값이 Bearer로 시작하면 이후 문자열을 jwt토큰으로 간주

    if (authorization != null && authorization.startsWith("Bearer ")) {
      String token = authorization.substring(7);

      if (jwtUtil.validateToken(token)) {
        String userId = jwtUtil.getUserId(token);
        //2.JWT유효성 검증 : jwtUtil.validateToken(token)을 호출하여 토큰의 유효성을 검증
        //유효한 토큰이라면 getUserId(token)을 통해 토큰에 저장된 사용자ID를 추출
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);
        //사용자 정보 조회: 추출한 사용자ID로 CustomUserDetailsService를 호출하여 사용자 정보를 가져온다.
        //UserDetails 객체는 SpringSecurity의 아용자 정보를 나타낸다.

        if (userDetails != null) { // 만약, 사용자 정보가 있으면 스프링 시큐리티의 인증 객체인 UsernamePasswordAuthenticationToken을 생성한다.
          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
              //userDetails.getAuthorities():사용자의 권한 정보를 설정한다.생성된 인증 객체를 SecurityContextHolder에 저장해서 현재
              //요청의 인증 상태를 설정한다.

          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
      }
    }
    filterChain.doFilter(request, response);//필터 체인의 다음 필터로 요청과 응답을 전달한다.
  }
}
//TP 요청의 헤더에서 JWT를 확인하고, 유효한 경우 사용자를 인증 상태로 설정합니다.
// 이를 통해 Spring Security가 해당 요청을 인증된 사용자로 간주하도록 만듭니다