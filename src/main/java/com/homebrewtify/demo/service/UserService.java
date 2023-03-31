package com.homebrewtify.demo.service;


import com.github.dozermapper.core.Mapper;
import com.homebrewtify.demo.config.BaseException;
import com.homebrewtify.demo.config.BaseResponseStatus;
import com.homebrewtify.demo.dto.UserDto;
import com.homebrewtify.demo.entity.User;
import com.homebrewtify.demo.repository.UserRepository;
import com.homebrewtify.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
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
        Optional<User> tt = Optional.ofNullable(mappedUser);
        if(tt.isEmpty())
            System.out.println("Null");
        System.out.println("mappedUser.getEmail()+\" / \"+  = " + mappedUser.getEmail() + " / " + mappedUser.getBirth());
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
    public UserDto.LoginRes login(UserDto.LoginReq loginReq) throws BaseException{
      //  String email = loginReq.getEmail();
         Optional<User> userByEmail = userRepository.findUserByEmail(loginReq.getEmail());
//        if(userByEmail.isEmpty()){ // 존재하는 이메일인지 확인
//            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
//        }
        log.info("로그인 잘되는지 확인중 --- 이메일 : {}",loginReq.getEmail());
        log.info("로그인 잘되는지 확인중 --- 비번 : {}",loginReq.getPassword());
        boolean userByEmailAndPassword = userRepository.existsByEmailAndPassword(loginReq.getEmail() , loginReq.getPassword());
        if(!userByEmailAndPassword){ // 존재하는 유저인지 확인

            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }
        System.out.println("** 로그인 확인 **");
        System.out.println(userByEmailAndPassword);
        System.out.println("** 로그인 확인 **");
        log.info("로그인 잘되는지 확인중 --- : {}",userByEmailAndPassword);

        User user = userByEmail.get();
        String jwt = jwtService.createJwt(user.getUserId());
        return UserDto.LoginRes.builder()
                .jwt(jwt)
                .userId(user.getUserId())
                .email(user.getEmail())
                .build();
    }

    /**
     * 회원 정보 조회
     */
    public UserDto.UserRes getUser(Long userId) throws BaseException{
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new BaseException(BaseResponseStatus.RESPONSE_ERROR);
        }
        User user = userOptional.get();
        return UserDto.UserRes.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

}
