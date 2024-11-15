package com.security.basic.domain.member.service.create;

import com.security.basic.domain.member.entity.Member;
import com.security.basic.domain.member.entity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateMemberService {
  private final MemberRepository memberRepository;

  public void createMember(Member member) {
    memberRepository.save(member);
  }
}
