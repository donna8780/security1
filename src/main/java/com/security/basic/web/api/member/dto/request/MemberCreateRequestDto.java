package com.security.basic.web.api.member.dto.request;

import com.security.basic.domain.member.entity.Member;
import com.security.basic.domain.member.enums.AuthType;
import com.security.basic.domain.member.enums.Gender;
import com.security.basic.domain.member.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MemberCreateRequestDto(
    @NotBlank
    String email,
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()-+=]).{8,}$")
    String password,
    @NotBlank
    String name,
    String gender,
    String phoneNumber
) {
    // of(String password) : Member 객체를 생성하는 역할을 합니다.
    // DTO의 필드 값을 Member 객체에 담아서 반환
    public Member of(String password) {
        return Member.builder()
            .email(email)//Member의 이메일 필드에 설정
            .name(name)//Member의 이름 필드에 설정
            .password(password)//외부에서 전달된 비밀번호를 Member 객체에 설정
            .phoneNumber(phoneNumber)// 사용자의 전화번호를 Member 객체에 설정
            .gender(Gender.valueOf(gender)) //ender.valueOf(gender)를 통해 gender 문자열을 Gender Enum으로 변환하여 설정합니다.
                                            // 만약 "MALE"이나 "FEMALE"과 같은 값이면, Gender Enum에서 해당 값을 찾습니다
            .role(Role.valueOf("USER"))   //role: 기본적으로 Role.valueOf("USER")로 "USER" 역할을 설정합니다.
                                                // 즉, 회원 가입 시 기본적으로 "USER" 역할을 가지게 됩니다.
            .authType(AuthType.valueOf("COMMON")) //AuthType.valueOf("COMMON")로 "COMMON" 인증 방식을 설정합니다. 즉, 일반적인 인증 방식이 기본으로 설정
            .build();
    }
}
/*이 코드는 회원 가입 요청을 위한 DTO를 정의
사용자로부터 받은 회원 가입 정보를 담고 있으며, 유효성 검증고가 함께 회원 객체(MEMBER)로 변환할 수 있는 기능을 제공한다.
회원 가입 시 사용자가 제출하는 이메일, 비밀번호, 이름, 성별, 전화번호를 담는 DTO입니다.
비밀번호 유효성 검증과 빈 값 체크를 위해 @NotBlank와 @Pattern 어노테이션이 사용됩니다.
of 메서드는 DTO의 데이터를 Member 엔티티로 변환하여 회원 객체를 생성
* */