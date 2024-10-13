package com.security.basic.domain.service;

import com.security.basic.domain.dto.req.create.CreateUserReqDto;
import com.security.basic.domain.dto.resp.UserRespDto;
import com.security.basic.domain.entity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String register(CreateUserReqDto req) {
        if (userRepository.userExists(req.username())) {
            throw new RuntimeException(String.format("User [%s] already exists", req.username()));
        }
        return userRepository.create(req).username();
    }

    public UserRespDto getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}
