package com.security.basic.global.security.member;

import com.security.basic.domain.member.service.dto.CustomUserInfoDto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
  //스프링 시큐리티가 사용자 정보를 이해할 수 있도록 변환해주는 역할
  //스프링 시큐리티에서 사용자의 로그인 요청을 처리할 때 호출되며, 사용자 정보와 권한을 기반으로
  //인증 및 권한 부여 과정을 지원
  private final CustomUserInfoDto member;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<String> roles = new ArrayList<>();
    roles.add("ROLE_" + member.role().toString());

    return roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
  /*사용자의 권한(roles)을 반환합니다.
  member.role()을 사용해 사용자의 역할을 가져옵니다.
  ROLE_ 접두어를 붙여 권한 문자열 생성. (예: ROLE_ADMIN, ROLE_USER)
  SimpleGrantedAuthority 객체로 변환해 Security가 이해할 수 있는 형태로 반환.*/

  @Override
  public String getPassword() {
    return member.password();
  }
  //사용자의 비밀번호를 반환. (Spring Security가 인증에 사용할 비밀번호)
  @Override
  public String getUsername() {
    return member.email();
  }
  //사용자의 고유한 이름(주로 이메일)을 반환.
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  //계정이 만료되지 않았는지 여부를 반환.
  //true: 계정이 만료되지 않음.
  //현재는 항상 true로 설정되어 있음.
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  //계정이 잠기지 않았는지 여부를 반환.
  //true: 계정이 잠기지 않음.
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  //자격 증명이 만료되지 않았는지 여부를 반환.
  //true: 자격 증명이 만료되지 않음.
  @Override
  public boolean isEnabled() {
    return true;
  }
  //계정이 활성화되었는지 여부를 반환.
  //true: 계정이 활성화됨.
}
