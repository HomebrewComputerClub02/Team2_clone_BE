package com.homebrewtify.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UpperGenre {
    private String name;
    private List<String> genres;
    private String color;
    private String imgUrl;
}