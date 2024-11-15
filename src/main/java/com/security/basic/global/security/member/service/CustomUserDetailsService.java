package com.security.basic.global.security.member.service;

import com.security.basic.domain.member.entity.Member;
import com.security.basic.domain.member.entity.repository.MemberRepository;
import com.security.basic.domain.member.service.dto.CustomUserInfoDto;
import com.security.basic.global.security.member.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member member = memberRepository.findMemberByEmail(username);
    CustomUserInfoDto customUserInfoDto = CustomUserInfoDto.of(member);
    return new CustomUserDetails(customUserInfoDto);
  }
}
