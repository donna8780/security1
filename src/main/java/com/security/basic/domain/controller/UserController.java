package com.security.basic.domain.controller;

import com.security.basic.domain.dto.req.create.CreateUserReqDto;
import com.security.basic.domain.dto.resp.ResultResponseDto;
import com.security.basic.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResultResponseDto<?> register(@RequestBody CreateUserReqDto req) {
        String result = userService.register(new CreateUserReqDto(req.username(), bCryptPasswordEncoder.encode(req.password())));
        return ResultResponseDto.ok(result);
    }
}
