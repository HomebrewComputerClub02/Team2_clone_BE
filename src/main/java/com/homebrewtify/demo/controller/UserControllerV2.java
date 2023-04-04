package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.config.BaseException;
import com.homebrewtify.demo.config.BaseResponse;
import com.homebrewtify.demo.config.jwt.JwtFilter;
import com.homebrewtify.demo.config.jwt.TokenProvider;
import com.homebrewtify.demo.dto.UserDto;
import com.homebrewtify.demo.entity.User;
import com.homebrewtify.demo.service.UserServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

import static com.homebrewtify.demo.config.jwt.JwtFilter.AUTHORIZATION_HEADER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserControllerV2 {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserServiceV2 userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    @PostMapping("/signup")
    public BaseResponse<UserDto.SignUpRes> createUser(@RequestBody @Valid UserDto.SignUpReq signUpReq){
        log.info("회원가입 정보 : ={}",signUpReq);
        log.info("회원가입 정보 - email ={}",signUpReq.getEmail());
        signUpReq.setPassword(bCryptPasswordEncoder.encode(signUpReq.getPassword()));
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
    @PostMapping("/login")
    public ResponseEntity<String> authorize(@Valid@RequestBody UserDto.LoginReq loginDto, HttpServletResponse response){
        System.out.println("in authenticate controller");
        String username = userService.getUserNameByEmail(loginDto.getEmail());

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username, loginDto.getPassword());
        try{
            log.info("before authenticate");
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            log.info("after authenticate");
            String accessJwt = tokenProvider.createToken(authentication,"Access");
            String refreshJwt = tokenProvider.createToken(authentication,"Refresh");

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + accessJwt);
            httpHeaders.add(JwtFilter.REFRESH_HEADER,"Bearer "+refreshJwt);
            log.info("in authenticate controller \nACCESS:{} \nREFRESH:{}",accessJwt,refreshJwt);


            userService.setRefreshToken(username,refreshJwt);

            Cookie cookie = new Cookie("RefreshToken",refreshJwt);
            //cookie.setHttpOnly(true);
            cookie.setPath("/");
            //cookie.setSecure(true);
            response.addCookie(cookie);

            return new ResponseEntity<>("Login Success", httpHeaders, HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getClass());
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
