package com.security.basic.web.api.member;

import com.security.basic.domain.member.facade.MemberFacade;
import com.security.basic.web.api.member.dto.request.LoginMemberRequestDto;
import com.security.basic.web.api.member.dto.request.MemberCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "MEMBER API", description = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberApiController {
  private final MemberFacade memberFacade;

  @Operation(summary = "회원 신규 가입")
  @PostMapping("/member")
  public ResponseEntity<?> createMember(@Valid @RequestBody MemberCreateRequestDto dto) {
    memberFacade.createMember(dto);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "로그인")
  @PostMapping("/login")
  public ResponseEntity<?> loginMember(@Valid @RequestBody LoginMemberRequestDto dto) {
    String token = memberFacade.login(dto);
    return ResponseEntity.status(HttpStatus.OK).body(token);
  }
}
