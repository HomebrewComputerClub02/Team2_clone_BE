package com.homebrewtify.demo.service;

import com.homebrewtify.demo.config.BaseException;
import com.homebrewtify.demo.config.BaseResponseStatus;
import com.homebrewtify.demo.config.jwt.TokenProvider;
import com.homebrewtify.demo.dto.UserDto;
import com.homebrewtify.demo.entity.User;
import com.homebrewtify.demo.repository.UserRepository;
import com.homebrewtify.demo.utils.Role;
import com.homebrewtify.demo.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceV2 {
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserDto.SignUpRes join(UserDto.SignUpReq dto){
        if(userRepository.findFirstByEmail(dto.getEmail()).orElse(null)!=null)
            throw new BaseException(BaseResponseStatus.POST_USERS_EXISTS_EMAIL);
        String username=dto.getEmail().substring(0,dto.getEmail().indexOf("@"));
        log.info("Make username:{}",username);
        User user = User.builder().email(dto.getEmail()).nickname(dto.getNickname()).birth(dto.getBirth()).gender(dto.getGender())
                .username(username).password(dto.getPassword()).role(Role.USER).build();
        User save = userRepository.save(user);
        return UserDto.SignUpRes.builder().userId(save.getUserId()).email(save.getEmail())
                .nickname(save.getNickname()).gender(save.getGender()).build();
    }
    public String getUserNameByEmail(String email) {
        User user = userRepository.findFirstByEmail(email).orElseThrow(() -> new BaseException(BaseResponseStatus.POST_USERS_EMPTY_EMAIL));
        return user.getUsername();
    }

    @Transactional
    public void setRefreshToken(String username,String refreshJwt) {
        User user = userRepository.findFirstByUsername(username).orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));
        user.setRefreshToken(refreshJwt);
    }
    @Transactional
    public void deleteRefresh() {
        String userName = SecurityUtil.getCurrentUsername().orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));
        System.out.println("userName = " + userName);
        User user = userRepository.findFirstByUsername(userName).orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));
        user.setRefreshToken("");
    }
    @Transactional
    public void deleteMember(Long memberId) {
        userRepository.deleteById(memberId);
    }
}
