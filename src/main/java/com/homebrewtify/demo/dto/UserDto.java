package com.homebrewtify.demo.dto;

import lombok.*;
import com.github.dozermapper.core.Mapping;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class LoginReq{ // 로그인 요청
        @Email
        @NotBlank
        private String email; // 이메일
        private String password; // 비번
    }

    @Getter
    @Builder
    public static class LoginRes{ // 로그인 응답
        private final String jwt;
        private Long userId; // 유저 고유값
        private String email; // 유저 이메일

    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class SignUpReq{ // 회원가입 요청
        // 이메일
        @Mapping("email")
        @NotBlank
        @Email
        private String email;

        // 비밀번호
        @Mapping("password")
        @NotBlank
        @Setter
        private String password;


        // 사용자(프로필) 이름
        @Mapping("nickname")
        @NotBlank
        private String nickname;


        // 생년월일
        @Mapping("birth")
        @NotBlank
        private String birth;

        // 성별
        @Mapping("gender")
        @NotBlank
        private String gender;


    }

    @Builder
    @Getter
    public static class SignUpRes{ // 회원가입 응답
        private String jwt;
        private Long userId;
        private String email;
        private String nickname;
        private String gender;

    }

    @Builder
    @Getter
    public static class UserRes{
        private String email;
        private String nickname;

    }




}
