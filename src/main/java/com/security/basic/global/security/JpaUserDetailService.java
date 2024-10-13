package com.security.basic.global.security;

import com.security.basic.domain.dto.resp.UserRespDto;
import com.security.basic.domain.entity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRespDto userByUsername = userRepository.getUserByUsername(username);
        return new CustomUserDetails(userByUsername);
    }
}
