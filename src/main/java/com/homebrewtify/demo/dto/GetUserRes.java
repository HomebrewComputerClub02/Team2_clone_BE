package com.homebrewtify.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {  // 회원 조회 응답
    private int userIdx;
    private String name;
    private String nickName;
    private String email;

}
