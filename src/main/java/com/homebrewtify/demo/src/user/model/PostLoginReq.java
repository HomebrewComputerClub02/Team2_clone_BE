package com.homebrewtify.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostLoginReq { // 로그인 요청
    private String email;
    private String password;
}

