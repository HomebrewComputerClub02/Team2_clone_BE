package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.config.BaseException;
import com.homebrewtify.demo.config.BaseResponse;
import com.homebrewtify.demo.config.BaseResponseStatus;
import com.homebrewtify.demo.dto.UserDto;
import com.homebrewtify.demo.service.UserService;
import com.homebrewtify.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {



    private final UserService userService;

    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }


    /**
     * 회원가입 API
     * @Requeset
     */
     @PostMapping("/signup")
     public BaseResponse<UserDto.SignUpRes> createUser(@RequestBody @Valid UserDto.SignUpReq signUpReq){
         log.info("회원가입 정보 : ={}",signUpReq);
         log.info("회원가입 정보 - email ={}",signUpReq.getEmail());
         log.info("회원가입 정보 - password ={}",signUpReq.getPassword());
         log.info("회원가입 정보 - birth ={}",signUpReq.getBirth());
         try{
                UserDto.SignUpRes result = userService.join(signUpReq);
               log.info("회원가입 결과 : {}",result);
                return new BaseResponse<>(result);
         }catch (BaseException e){
             return new BaseResponse<>(e.getStatus());
         }
     }



    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public BaseResponse<UserDto.LoginRes> login(@RequestBody @Valid UserDto.LoginReq loginReq){
        log.info("로그인 정보:={}",loginReq);
        log.info("로그인 정보- email ={}",loginReq.getEmail());
        log.info("로그인 정보- password ={}",loginReq.getPassword());
        try{
            UserDto.LoginRes result = userService.login(loginReq);
            log.info("로그인 결과 : {}",result);
            return new BaseResponse<>(result);

        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }

    /**
     * 회원정보 조회 API
     */
    @GetMapping("/{userId}")
    public BaseResponse<UserDto.UserRes> getUser(@PathVariable Long userId){
        try{
            Long userIdByJwt = jwtService.getUserId();
            if(!userIdByJwt.equals(userId)){
                throw new BaseException(BaseResponseStatus.INVALID_JWT);
            }
            UserDto.UserRes result = userService.getUser(userId);
            log.info("회원정보조회 결과 : {}",result);
            return new BaseResponse<>(result);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
