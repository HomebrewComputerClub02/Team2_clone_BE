package com.homebrewtify.demo.contorller;

import com.homebrewtify.demo.config.BaseException;
import com.homebrewtify.demo.config.BaseResponse;
import com.homebrewtify.demo.dto.PostLoginReq;
import com.homebrewtify.demo.dto.PostSignUpReq;
import com.homebrewtify.demo.dto.UserDto;
import com.homebrewtify.demo.service.UserProvider;
import com.homebrewtify.demo.service.UserService;
import com.homebrewtify.demo.dto.GetUserRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.homebrewtify.demo.config.BaseResponseStatus.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {


    private final UserProvider userProvider;

    private final UserService userService;


    @Autowired
    public UserController(UserProvider userProvider, UserService userService) {
        this.userProvider = userProvider;
        this.userService = userService;
    }
    /**
     * 회원 조회 API
     * [GET] /users
     */
    @GetMapping("")
    public BaseResponse<GetUserRes> getUsers(@RequestParam(required = true) String Email){
        try{
            // 이메일로 조회 예시
            if(Email.length()==0){
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }

            GetUserRes getUserRes = userProvider.getUsersByEmail(Email);
            return new BaseResponse<>(getUserRes);

        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 회원가입 API
     * @Requeset
     */
     @PostMapping("/signup")
     public String signup(@RequestBody PostSignUpReq postSignUpReq){
         try{

         }
     }



    /**
     * 로그인 API
     *
     */
    @PostMapping("/login")
    public String login(@RequestBody PostLoginReq postLoginReq){
        log.info("로그인 정보:={}",postLoginReq);
        // 로그인 처
        return "로그인 성공";
    }

}
