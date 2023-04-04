package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.config.BaseException;
import com.homebrewtify.demo.config.BaseResponseStatus;
import com.homebrewtify.demo.entity.User;
import com.homebrewtify.demo.repository.UserRepository;
import com.homebrewtify.demo.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
@RestController
@Slf4j
public class AuthTestController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/noAuthOk")
    @ResponseBody
    public String AuthTest() {
        return "Test noAuth Success";
    }
    @GetMapping("/needAuth")
    @ResponseBody
    public String AuthTest2() {
        return "Test auth Success";
    }
    @PostMapping("/pressLogout")
    @Transactional
    public ResponseEntity<String> logout(HttpServletResponse response){
        log.info("logout called");
        String userName = SecurityUtil.getCurrentUsername().orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));
        System.out.println("userName = " + userName);
        User user = userRepository.findFirstByUsername(userName).orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));
        user.setRefreshToken("");
        //쿠키 삭제..?
        expireCookie(response,"RefreshToken");
        return new ResponseEntity<>("Logout Success", HttpStatus.OK);
    }
    private static void expireCookie(HttpServletResponse response,String name) {
        Cookie cookie=new Cookie(name, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
