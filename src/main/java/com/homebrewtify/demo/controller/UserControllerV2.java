package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.config.BaseException;
import com.homebrewtify.demo.config.BaseResponse;
import com.homebrewtify.demo.config.BaseResponseStatus;
import com.homebrewtify.demo.config.jwt.JwtFilter;
import com.homebrewtify.demo.config.jwt.TokenProvider;
import com.homebrewtify.demo.dto.UserDto;
import com.homebrewtify.demo.entity.User;
import com.homebrewtify.demo.repository.UserRepository;
import com.homebrewtify.demo.service.UserServiceV2;
import com.homebrewtify.demo.utils.SecurityUtil;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/signup")
    public BaseResponse<UserDto.SignUpRes> createUser(@Valid@RequestBody UserDto.SignUpReq signUpReq){
        log.info("회원가입 정보 : ={}",signUpReq);
        log.info("회원가입 정보 - email ={}",signUpReq.getEmail());
        String password = signUpReq.getPassword();
        signUpReq.setPassword(bCryptPasswordEncoder.encode(signUpReq.getPassword()));
        log.info("회원가입 정보 - password ={}",signUpReq.getPassword());
        log.info("회원가입 정보 - birth ={}",signUpReq.getBirth());
        try{
            UserDto.SignUpRes result = userService.join(signUpReq);
            String[] split = result.getEmail().split("@");
            log.info("로그인아이디 :{} 비밀번호 :{}",split[0],password);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(split[0], password);
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            String accessJwt = tokenProvider.createToken(authentication,"Access");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + accessJwt);
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
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            //cookie.setSecure(true);
            response.addCookie(cookie);

            return new ResponseEntity<>(accessJwt, httpHeaders, HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getClass());
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response){
        log.info("logout called");
        userService.deleteRefresh();
        expireCookie(response,"RefreshToken");
        return new ResponseEntity<>("Logout Success", HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMember(@RequestParam("memberId") Long memberId){
        userService.deleteMember(memberId);
        return new ResponseEntity<>("User Deleted",HttpStatus.OK);
    }

    private static void expireCookie(HttpServletResponse response,String name) {
        Cookie cookie=new Cookie(name, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
