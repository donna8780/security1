package com.security.basic.global.config.security;

import com.security.basic.global.jwt.filter.JwtFilter;
import com.security.basic.global.jwt.util.JwtUtil;
import com.security.basic.global.security.handler.CustomAccessDeniedHandler;
import com.security.basic.global.security.handler.CustomAuthenticationEntryPoint;
import com.security.basic.global.security.member.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
  private final CustomUserDetailsService customUserDetailsService;
  private final JwtUtil jwtUtil;
  private final CustomAccessDeniedHandler accessDeniedHandler;
  private final CustomAuthenticationEntryPoint authenticationEntryPoint;

  //화이트리스트 경로 :인증 없이 접근 가능한 경로 예) 로그인  페이지, 회원가입 API, 문서화 페이지 등 인증이 필요하지 않은 경로를 지정
  private static final String[] AUTH_WHITELIST = {
      "/api/v1/member/**", "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
      "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html", "/api/v1/auth/**", "/api/v1/member"
  };

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  //비밀번호 암호화를 위한 BCryptPasswordEncoder를 반환, Spring Security에서 사용자 인증 시 입력된 비밀번호와 저장된 암호화 비밀번호를 비교할 때 사용
  @Bean
  //스프링 시큐리티의 보안 필터 체인을 설정한다.
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .addFilterBefore(new JwtFilter(customUserDetailsService, jwtUtil), UsernamePasswordAuthenticationFilter.class)
        //jwt필터를 스프링 시큐리티 필터 체인에 추가한다. UsernamePasswordAuthenticationFilter전에 실행 되도록 설정
        .exceptionHandling(c -> c.authenticationEntryPoint((authenticationEntryPoint))
            .accessDeniedHandler(accessDeniedHandler))
        //CustomAutenticationEntryPoint:인증되지 않은 요청이 들어왔을 때 처리
        //CustomAccessDeniedHandeler: 권한이 부족한 요청이 들어왔을 때 처리
        .authorizeHttpRequests(c -> c.requestMatchers(AUTH_WHITELIST).permitAll()
            .anyRequest().permitAll())
        //AUTH_WHITELIST에 정의된 경로는 인증 없이 접근 가능 그 외의 모든 요청도 permitAll로 설정되어 현재는 모두 허용
        //이후 권한 설정을 추가하면 수정 가능
        .build();
        //최종적으로 설정된 보안 필터 체인을 반환
  }
}
/*이해하기 쉽게 비유

* 이 클래스는 웹사이트 입구의 보안 관리자
* 요청이 들어올 때:
* 입장권(jwt)를 확인한다.
* 입장권이 유효하면 사용자를 인증한다.
* 특정 입구(화이트리스트 경로)는 입장권 없이도 들어갈 수 있게 허용한다.
* */
