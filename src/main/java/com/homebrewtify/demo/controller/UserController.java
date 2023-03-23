package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.config.BaseException;
import com.homebrewtify.demo.config.BaseResponse;
import com.homebrewtify.demo.dto.UserDto;
import com.homebrewtify.demo.service.UserService;
import com.homebrewtify.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
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
         try{
                UserDto.SignUpRes result = userService.join(signUpReq);
                return new BaseResponse<>(result);
         }catch (BaseException e){
             return new BaseResponse<>(e.getStatus());
         }
     }



    /**
     * 로그인 API
     *
     */
    @PostMapping("/login")
    public String login(@RequestBody UserDto.LoginReq loginReq){
        log.info("로그인 정보:={}",loginReq);
        // 로그인 처
        return "로그인 성공";
    }

}
