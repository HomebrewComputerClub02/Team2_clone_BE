package com.homebrewtify.demo.utils;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("손님"),
    USER("일반 사용자");

    private final String title;

}
