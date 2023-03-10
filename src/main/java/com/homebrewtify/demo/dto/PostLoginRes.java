package com.homebrewtify.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostLoginRes { // 로그인 응답
    private int userIdx;
    private String jwt;
}

