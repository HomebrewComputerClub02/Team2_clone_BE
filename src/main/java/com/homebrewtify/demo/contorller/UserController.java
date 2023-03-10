package com.homebrewtify.demo.contorller;

import com.homebrewtify.demo.config.BaseException;
import com.homebrewtify.demo.config.BaseResponse;
import com.homebrewtify.demo.service.UserProvider;
import com.homebrewtify.demo.service.UserService;
import com.homebrewtify.demo.dto.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.homebrewtify.demo.config.BaseResponseStatus.*;


@RestController
@RequestMapping("/users")
public class UserController {


    private final UserProvider userProvider;

    private final UserService userService;




    @Autowired
    public UserController(UserProvider userProvider, UserService userService){
        this.userProvider = userProvider;
        this.userService = userService;
    }

    /**
     * 회원 조회 API
     * [GET] /users
     */
    @ResponseBody
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
}
