package com.security.basic.domain.member.entity.repository;

import com.security.basic.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Member findMemberByEmail(String email);
}
