package com.homebrewtify.demo.service;


import com.github.dozermapper.core.Mapper;
import com.homebrewtify.demo.config.BaseException;
import com.homebrewtify.demo.config.BaseResponseStatus;
import com.homebrewtify.demo.dto.UserDto;
import com.homebrewtify.demo.entity.User;
import com.homebrewtify.demo.repository.UserRepository;
import com.homebrewtify.demo.utils.JwtService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = BaseException.class)
public class UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final Mapper mapper;

    public UserService(UserRepository userRepository, JwtService jwtService, Mapper mapper) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.mapper = mapper;
    }


    /**
     * 회원가입 처리
     */
    public UserDto.SignUpRes join(UserDto.SignUpReq signUpReq) throws BaseException {
        if(userRepository.existsByEmail(signUpReq.getEmail())){ // 이메일 중복 확인
            throw new BaseException(BaseResponseStatus.POST_USERS_EXISTS_EMAIL);
        }

        User mappedUser = mapper.map(signUpReq,User.class);
        User user = userRepository.save(mappedUser);

       String jwt = jwtService.createJwt(user.getUserId()); // 해당 유저의 jwt 토큰 생성
        return UserDto.SignUpRes.builder()
                .jwt(jwt)
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .build();
    }

    /**
     * 로그인 처리
     */


}
