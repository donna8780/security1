package com.security.basic.domain.service;

import com.security.basic.domain.dto.req.create.CreateUserReqDto;
import com.security.basic.domain.dto.resp.UserRespDto;
import com.security.basic.domain.entity.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService sut;

    @Test
    @DisplayName("user 가 존재하면 runtime exception 을 발생시킨다.")
    void registerFailureCase() {
        // given
        CreateUserReqDto user = new CreateUserReqDto(
                "danny.kim",
                "12345"
        );
        given(userRepository.userExists(user.username()))
                .willReturn(true);

        // when & then
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class,
                () -> sut.register(user)
        );
        Assertions.assertEquals(
                runtimeException.getMessage(),
                "User [danny.kim] already exists"
        );
    }

    @Test
    @DisplayName("user 가 존재하지 않으면 회원가입을 수행한다.")
    void registerSuccessCase() {
        // given
        CreateUserReqDto user = new CreateUserReqDto(
                "danny.kim",
                "12345"
        );
        given(userRepository.userExists(user.username()))
                .willReturn(false);
        given(userRepository.create(user))
                .willReturn(UserRespDto.builder()
                        .username(user.username())
                        .build());

        // when
        String register = sut.register(user);

        // then
        Assertions.assertEquals(register, user.username());
        verify(userRepository, times(1))
                .create(user);
    }

}